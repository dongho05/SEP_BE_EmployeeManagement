package com.project.SEP_BE_EmployeeManagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contract")
@Data
public class Contract extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "contract_name", nullable = false, length = 200)
    private String contractName;

    @Column(name = "file_name", nullable = false)
    private String fileName;

//    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
//    // MapopedBy trỏ tới tên biến Address ở trong Person.
////    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
////    @ToString.Exclude // Khoonhg sử dụng trong toString()
//    private Set<User> users;

    @ManyToOne
    @JoinColumn(name = "user_id") // thông qua khóa ngoại contract_id
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
