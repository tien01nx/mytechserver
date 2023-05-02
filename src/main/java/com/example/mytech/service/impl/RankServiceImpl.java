package com.example.mytech.service.impl;


import com.example.mytech.entity.Course;
import com.example.mytech.entity.Rank;
import com.example.mytech.entity.User;
import com.example.mytech.exception.InternalServerException;
import com.example.mytech.exception.NotFoundException;
import com.example.mytech.model.request.RankReq;
import com.example.mytech.repository.RankRepository;
import com.example.mytech.service.CourseService;
import com.example.mytech.service.RankService;
import com.example.mytech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RankServiceImpl implements RankService {

    @Autowired
    private RankRepository rankRepository ;

    @Override
    public void addGrade(User user, Course course, RankReq req) {
        Rank rank = rankRepository.findByUserAndCourse(user, course) ;

        if (rank == null) {
            rank = new Rank() ;
            rank.setUser(user);
            rank.setCourse(course);
        }
        rank.setMidtermGrades(req.getMidtermGrades());
        rank.setFinalGrades(req.getFinalGrades());
        rank.setExams(req.getExams());
        rank.setAvg((req.getMidtermGrades() + req.getFinalGrades() + req.getExams()) / 3);

        if (rank.getAvg() >= 5) {
            rank.setRanking("Pass");
        } else {
            rank.setRanking("Fail");
        }
        try{
            rankRepository.save(rank);
        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi thêm điểm cho học viên");
        }
    }

    @Override
    public Rank updateRank(String course_id, String user_id, RankReq req) {

        Rank existingRank = rankRepository.findByCourseIdAndUserId(course_id, user_id);
        if (existingRank == null) {
            throw new NotFoundException("Rank not found");
        }
        existingRank.setMidtermGrades(req.getMidtermGrades());
        existingRank.setFinalGrades(req.getFinalGrades());
        existingRank.setExams(req.getExams());

        existingRank.setAvg((req.getMidtermGrades() + req.getFinalGrades() + req.getExams()) / 3);

        if (existingRank.getAvg() >= 5) {
            existingRank.setRanking("Pass");
        } else {
            existingRank.setRanking("Fail");
        }
        try{
            rankRepository.save(existingRank);
        }
        catch (Exception e) {
            throw new InternalServerException("Lỗi khi cập nhật điểm cho học viên");
        }
        return existingRank;
    }

    @Override
    public List<Rank> getRanksByCourseId(String course_id) {
        return rankRepository.findByCourseId(course_id);
    }

    @Override
    public void deleteRankById(String id) {
        rankRepository.deleteById(id);
    }

    @Override
    public RankReq getGradesByUserIdAndCourseId(String userId, String courseId) {
        Rank rank = rankRepository.findByUserIdAndCourseId(userId, courseId);
        if (rank != null) {
            RankReq rankReq = new RankReq(rank.getMidtermGrades(), rank.getFinalGrades(), rank.getExams(),rank.getAvg(),rank.getRanking());
            return rankReq;
        }
        return null;
    }
}