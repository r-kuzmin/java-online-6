package by.epam.training.task1.bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class User extends Person {

	private static final long serialVersionUID = 5981240122055258913L;
	public static final String EMAIL_REGEX = "^\\S+@\\S+\\.\\S+$";

	private int id;
	private String login;
	private String hash;
	private String salt;
	private int role;
	private String email;

	public User() {
		this("", "", "", 0);
	}

	public User(String name, String login, String password, int role) {
		super(name);
		this.id = 0;
		this.role = role;
		this.login = login;
		this.hash = pwdHash(password);
	}

	@Override
	public User clone() {
		
		User image = new User(getName(), login, null, role);
		
		image.setId(id);
		image.setHash(hash);
		image.setSalt(salt);
		image.setEmail(email);
		
		return image;
		
	}

	public boolean setPassword(String pwd) {

		String hash = pwdHash(pwd);
		if (hash == null) {
			return false;
		}

		this.hash = hash;

		return true;
	}

	public boolean checkPassword(String pwd) {

		String hash = pwdHash(pwd);
		if (hash == null) {
			return false;
		}

		return hash.equals(this.hash);
	}

	/**
	 * Генерирует hash для указанного пароля. В случае, если у пользователя не
	 * заполнено поле salt, оно будет заполнено случайным значением. Наличие
	 * различных salt для всех пользователей не даёт найти тех пользователей, у
	 * которых одинаковые пароли по одинаковым hash.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String pwdHash(String pwd) {

		String hash = null;

		if (salt == null || salt.isEmpty()) {
			byte[] byteArray = new byte[32];
			Random rnd = new Random();
			rnd.nextBytes(byteArray);
			salt = readable(byteArray);
		}

		MessageDigest md;
		String localSecretSalt = "55781138-6bad-4f73-b888-446433d5edb7";

		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] input = (pwd + salt + localSecretSalt).getBytes("UTF-8");
			byte[] output = md.digest(input);
			hash = readable(output);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hash;
	}

	/**
	 * Конвертирует массив байтов в читаемую строку. Оставляет только цифры и буквы.
	 * 
	 * @param array
	 * @return
	 */
	private String readable(byte[] array) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			byte b = (byte) Math.abs(array[i]);
			if ((b >= 48 && b <= 57) || (b >= 65 && b <= 90) || (b >= 97 && b <= 122)) {
				sb.append((char) b);
			}
		}

		return sb.toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.isBlank() || email.matches(User.EMAIL_REGEX)) {
			this.email = email;
		} 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
		result = prime * result + role;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (!super.equals(obj))
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		User other = (User) obj;
		
		if (id != other.id)
			return false;
		
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		
		if (hash == null) {
			if (other.hash != null)
				return false;
		} else if (!hash.equals(other.hash))
			return false;
		
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
			return false;
		
		if (role != other.role)
			return false;

		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", hash=" + hash + ", salt=" + salt + ", role=" + role + ", email=" + email + "]";
	}

}
