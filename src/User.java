public class User {
  private String username; //The username this User tweets under
  private boolean isVerified;
	  //The verified status of this User (whether they have a blue checkmark or not)

  //constructor
  public User(String username) {
	if (username.contains("*") || username.isBlank() || username.equals(null)) {
	  throw new IllegalArgumentException("invalid username");
	}
	this.username = username;
	this.isVerified = false;
  }

  public String getUsername() {
	return username;
  }

  public boolean isVerified() {
	return isVerified;
  }

  public void verify() {
	this.isVerified = true;
  }

  public void revokeVerification() {
	this.isVerified = false;
  }

  @Override
  public String toString() {
	return "@" + username + (isVerified ? "*" : "");
  }
}
