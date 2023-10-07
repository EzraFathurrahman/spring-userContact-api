package ezraspring.restful.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name="contacts")
@AllArgsConstructor
@NoArgsConstructor

public class Contact {


    @Id
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    private String email;


    @ManyToOne
    @JoinColumn(name="username",referencedColumnName = "username")
    private User user;

    @OneToMany(mappedBy = "contact")
    private List<Address> addresses;

}
