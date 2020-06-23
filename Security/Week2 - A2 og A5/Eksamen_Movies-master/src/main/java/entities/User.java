package entities;

import errorhandling.AuthenticationException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
        @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
        @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList();

    @Column(name = "failed_login", columnDefinition = "varchar(255) default null")
    private String lastFailedLogin;

    public List<String> getRolesAsStrings() {
        if (roleList.isEmpty()) {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList();
        for (Role role : roleList) {
            rolesAsStrings.add(role.getRoleName());
        }
        return rolesAsStrings;
    }

    public User() {
    }

    //TODO Change when password is hashed
    public boolean verifyPassword(String pw) throws AuthenticationException {
        try {
            if (!this.loginAllowed() || !BCrypt.checkpw(pw, userPass)) {
                this.setLastFailedLogin();
                return false;
            }
            return true;
        } catch (ParseException ex) {
            throw new AuthenticationException("Last login date could not be formatted");
        }
    }

    public User(String userName, String userPass) {
        this.userName = userName;
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public void addRole(Role userRole) {
        roleList.add(userRole);
    }

    public void setLastFailedLogin(String lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
    }
    
    public String getFailedLogin() {
        return this.lastFailedLogin;
    }
    
    public Date getLastFailedLogin() throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(this.lastFailedLogin);
    }
 
    public void setLastFailedLogin() {
        this.lastFailedLogin = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }
    
    public boolean loginAllowed() throws ParseException {
        if (this.lastFailedLogin == null) {
            return true;
        }
        Date now = new Date();
        long difference = now.getTime() - this.getLastFailedLogin().getTime();
        return difference > 30000;

    }

}
