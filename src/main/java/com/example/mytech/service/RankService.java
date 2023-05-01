package com.example.mytech.service;

import com.example.mytech.entity.Course;
import com.example.mytech.entity.Rank;
import com.example.mytech.entity.User;
import com.example.mytech.model.request.RankReq;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface RankService {


    public void addGrade(User user, Course course, RankReq req);

    public Rank updateRank(String id, String user_id, RankReq req);

    public List<Rank> getRanksByCourseId(String course_id);

    public void deleteRankById(String id);
    RankReq getGradesByUserIdAndCourseId(String userId, String courseId);
}