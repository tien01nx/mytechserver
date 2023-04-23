package com.example.mytech.entity;

import com.example.mytech.model.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "categoryDTO",
                        classes = @ConstructorResult(
                                targetClass = CategoryDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = String.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "course_count", type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListCategoryAndCourseCount",
        resultSetMapping = "categoryDTO",
        query = ( "SELECT category.id, category.name, COUNT(course.id) as course_count\n" +
                "FROM category\n" +
                "INNER JOIN course ON category.id = course.category_id"
        )
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @GenericGenerator(name = "random_id", strategy = "com.example.mytech.model.custom.RandomIdGenerator")
    @Id
    @GeneratedValue(generator = "random_id")
    private String id;

    @Column(name = "name" , nullable = false)
    private String name ;

    @Column(name = "slug")
    private String slug ;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column (name = "modified_at")
    private Timestamp modifiedAt;

}
