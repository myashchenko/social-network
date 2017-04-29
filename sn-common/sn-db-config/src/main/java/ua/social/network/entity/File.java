package ua.social.network.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@Entity
@Table(name = "file")
public class File extends BaseEntity {
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "user", nullable = false)
    private String user;
}
