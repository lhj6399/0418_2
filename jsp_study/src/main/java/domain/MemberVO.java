package domain;

public class MemberVO {
	
	private String id;
	private String pwd;
	private String email;
	private int age;
	private String phone;
	private String regdate;
	private String lastlogin;
	
	public MemberVO() {}
	
	//login : id, pwd
	public MemberVO(String id, String pwd) {
		this.id = id;
		this.pwd = pwd;
	}
	
	//join, modify : id, pwd, email, age, phone
	public MemberVO(String id, String pwd, String email, int age, String phone) {
		this(id, pwd);
		this.email = email;
		this.age = age;
		this.phone = phone;
	}
	//all :
	public MemberVO(String id, String pwd, String email, int age, String phone, String regdate, String lastlogin) {
		this(id, pwd,email,age,phone);
		this.regdate = regdate;
		this.lastlogin = lastlogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pwd=" + pwd + ", email=" + email + ", age=" + age + ", phone=" + phone
				+ ", regdate=" + regdate + ", lastlogin=" + lastlogin + "]";
	}
	
}
