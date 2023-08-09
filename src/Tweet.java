import java.util.Calendar;
import java.util.Date;

public class Tweet {
  private static Calendar dateGenerator;
  //A shared Calendar object for this class to use to generate consistent dates. I
  private User user; //The User associated with this tweet
  private String text; //The text of this tweet
  private int numLikes; //The number of likes this tweet has
  private int numRetweets; //The number of retweets this tweet has
  private Date timestamp;
  //The date and time this tweet was posted, a Date object
  // created by calling dateGenerator.getTime() at construction time
  private static final int MAX_LENGTH = 280;
  //A value determining the maximum length of a tweet. Set to 280 by default.

  //constructor

  public Tweet(User user, String text)
	  throws IllegalArgumentException, NullPointerException, IllegalStateException {
	if (dateGenerator==null)
	  throw new IllegalStateException("dateGenerator not initialized");
	if (text.length() > MAX_LENGTH)
	  throw new IllegalArgumentException("tweet exceeds length");
	if (user.equals(null) || text.equals(null))
	  throw new NullPointerException("null user or text");

	this.user = user;
	this.text = text;
	this.numLikes = 0;
	this.numRetweets = 0;
	this.timestamp = dateGenerator.getTime();
  }

  //methods
  public static void setCalendar(Calendar c) {
	dateGenerator = c;
  }

  public String getText() {
	return text;
  }

  public int getTotalEngagement() {
	return numLikes + numRetweets;
  }

  public boolean isUserVerified() {
	return user.isVerified();
  }

  public double getLikesRatio() {
	if (getTotalEngagement() == 0)
	  return -1;
	return ((double) numLikes) / (double) getTotalEngagement();
  }

  public void like() {
	numLikes++;
  }

  public void retweet() {
	numRetweets++;
  }

  public boolean equals(Object o) {
	if (o instanceof Tweet) {
	  Tweet twt = (Tweet) o;
	  if (this.text == twt.text && this.user.toString().equals(twt.user.toString()))
		return true;
	}
	return false;
  }
  public String toString(){
	return  "tweet from " + this.user.toString() + " at " + timestamp.toString() + ":" +
		"\n-- " + this.text + "\n-- likes: " + this.numLikes + "\n-- retweets: " + this.numRetweets;
  }
}
