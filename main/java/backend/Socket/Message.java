package backend.Socket;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column
    private String emailid;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date sent = new Date();

    public Message(){};

    public Message(String emailid, String content) {
        this.emailid = emailid;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailid() {
        return this.emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateSent() {
        return this.sent;
    }

    public void setDateSent(Date sent) {
        this.sent = sent;
    }
}
