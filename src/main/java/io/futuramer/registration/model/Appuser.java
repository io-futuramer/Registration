package io.futuramer.registration.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="USERS")
@TypeDef(
		name = "gender_type",
		typeClass = GenderType.class
)
public class Appuser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min=3, max=50, message="{Size.appuser.surname}")
	@Column(name = "SURNAME", nullable = false)
	private String surname;

	@Size(min=3, max=50, message="{Size.appuser.firstname}")
	@Column(name = "FIRSTNAME", nullable = false)
	private String firstname;

	@NotNull(message="{NotNull.appuser.gender}")
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", nullable = false)
	@Type(type = "gender_type")
	private Gender gender;

	@NotNull(message="{NotNull.appuser.birthdate}")
	@DateTimeFormat(pattern="dd.MM.yyyy")
	@Column(name = "BIRTHDATE", nullable = false)
	private Date birthdate;

	@NotBlank(message="{NotBlank.appuser.email}")
	@Email
	@Size(max=100)
	@Column(name = "EMAIL", nullable = false)
	private String email;

	@NotBlank(message="{NotBlank.appuser.username}")
	@Size(min=3, max=50, message="{Size.appuser.username}")
	@Column(name = "USERNAME", nullable = false)
	private String username;

	@NotBlank(message="{NotBlank.appuser.password}")
	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "VERSION", nullable = false)
	private Integer version;

	public enum Gender {
		MALE("male"),
		FEMALE ("female");

		String value;

		Gender(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Gender fromString(String text) {
			for (Gender gender : Gender.values()) {
				if (gender.value.equalsIgnoreCase(text)) {
					return gender;
				}
			}
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender != null ? gender.getValue() : "";
	}

	public void setGender(String gender) {
		this.gender = Gender.fromString(gender);
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
