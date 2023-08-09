import java.util.Calendar;
import java.util.NoSuchElementException;

public class TwiteratorTester {
  public static boolean testUser() {
	//testing an invalid username
	try {
	  User u1 = new User("voldemort*");
	  return false;
	} catch (IllegalArgumentException ex) {
	}

	User u2 = new User("voldemort");
	if (u2.isVerified())   //initially new user is not verified
	  return false;
	u2.verify();
	if (!u2.isVerified())    //after verifying
	  return false;
	if (!u2.toString().equals("@voldemort*"))    //verifying adds * to the end of username
	  return false;
	u2.revokeVerification();    //sets isverified to false; * is removed from username
	if (u2.isVerified())
	  return false;
	return u2.toString().equals("@voldemort");
  }

  public static boolean testTweet() {
	User u1 = new User("voldemort");
	//testing making a tweet without initializing dateGenerator obj first:
	try {
	  Tweet twt = new Tweet(u1, "i am tom riddle");
	  return false;
	} catch (IllegalStateException ex) {
	}
	//testing making a tweet with text exceeding limit
	Calendar test = Calendar.getInstance();
	test.set(2012, Calendar.MAY, 22, 14, 46, 03);
	Tweet.setCalendar(test);
	try {
	  Tweet twt = new Tweet(u1,
		  "Darkness shall reign eternal, as I, Lord Voldemort, master of the Dark Arts, " + "cast my formidable gaze upon the feeble world. Wizards and Muggles alike, " + "tremble before my power, for I am the embodiment of fear, the harbinger of doom. " + "The echoes of my name shall resound through history, a testament to my supremacy " + "over life and death. #DarkLord #EternalDarkness #VoldemortSupremacy");
	  return false;
	} catch (IllegalArgumentException ex) {
	}
	//testing a tweet with null username
	try {
	  Tweet twt = new Tweet(null, "i am tom riddle");
	  return false;
	} catch (NullPointerException ex) {
	}

	Tweet twt = new Tweet(u1, "i am tom riddle");
	if (twt.getTotalEngagement() != 0)  //fresh tweet has 0 likes and retweets
	  return false;
	if (twt.getLikesRatio() != -1)  //tweet with 0 engagement has -1 likes ratio
	  return false;
	twt.like();  //liking the tweet
	twt.retweet();  //retweeting the tweet
	if (twt.getTotalEngagement() != 2)  //total engangement = likes + retweets
	  return false;
	if (twt.getLikesRatio() != 0.5)   //likes ratio = likes/total eng
	  return false;
	if (twt.isUserVerified())  //new user is not verified
	  return false;
	if (!twt.getText().equals("i am tom riddle"))   //text is tweet content
	  return false;
	Tweet twt2 =
		new Tweet(u1, "i am tom riddle");   //both are equal if by same user and has same text
	if (!twt.equals(twt2))
	  return false;
	String s1 = twt.toString();
	String s2 = """
		tweet from @voldemort at Tue May 22 14:46:03 GST 2012:
		-- i am tom riddle
		-- likes: 1
		-- retweets: 1""";
	return s1.equals(s2);
  }

  public static boolean testNode() {
	Tweet twt1 = new Tweet(new User("harryP"), "i am the chosen one");
	Tweet twt2 = new Tweet(new User("voldemort"), "i am tom riddle");
	TweetNode node2 = new TweetNode(twt2);
	TweetNode node1 = new TweetNode(twt1, node2);
	if (!node1.getNext().equals(node2) && !node1.getTweet().equals(twt1))
	  return false;
	Tweet tw3 = new Tweet(new User("dumbledoor"), "i am the principal");
	TweetNode node3 = new TweetNode(tw3);
	node2.setNext(node3);
	return node2.getNext().equals(node3) && node2.getTweet().equals(twt2);
  }

  public static boolean testAddTweet() {
	TwitterFeed feed = new TwitterFeed();
	Tweet twt = new Tweet(new User("voldemort"), "i am tom riddle");
	if (feed.size() != 0 && !feed.isEmpty() && feed.indexOf(twt) != -1 && feed.contains(twt))
	  return false;
	try {
	  feed.getHead();
	  return false;
	} catch (NoSuchElementException ex) {
	}
	try {
	  feed.getTail();
	  return false;
	} catch (NoSuchElementException ex) {
	}
	feed.addFirst(twt);
	{
	  int expected1 = 1;
	  int actual1 = feed.size();

	  boolean expected2 = false;
	  boolean actual2 = feed.isEmpty();

	  Tweet expected3 = twt;
	  Tweet actual3 = feed.get(0);

	  Tweet expected4 = twt;
	  Tweet actual4 = feed.getHead();

	  Tweet expected5 = twt;
	  Tweet actual5 = feed.getTail();

	  boolean expected6 = true;
	  boolean actual6 = feed.contains(twt);

	  int expected7 = 0;
	  int actual7 = feed.indexOf(twt);

	  if (expected1 != actual1 || expected2 != actual2 || !expected3.equals(
		  actual3) || !expected4.equals(actual4) || !expected5.equals(
		  actual5) || expected6 != actual6 || expected7 != actual7)
		return false;
	}
	try {
	  feed.get(2);
	  return false;
	} catch (IndexOutOfBoundsException ex) {
	}
	return true;
  }

  public static boolean testInsertTweet() {
	TwitterFeed feed = new TwitterFeed();
	Tweet twt1 = new Tweet(new User("voldemort"), "i am tom riddle");
	Tweet twt2 = new Tweet(new User("harryP"), "i am the chosen one");
	Tweet twt3 = new Tweet(new User("dumbledoor"), "i am the principal");
	feed.addFirst(twt1);
	feed.addLast(twt3);
	feed.add(1, twt2);

	{
	  int expected1 = 3;
	  int actual1 = feed.size();

	  boolean expected2 = false;
	  boolean actual2 = feed.isEmpty();

	  Tweet expected3 = twt3;
	  Tweet actual3 = feed.get(2);

	  Tweet expected4 = twt1;
	  Tweet actual4 = feed.get(0);

	  Tweet expected5 = twt2;
	  Tweet actual5 = feed.get(1);

	  boolean expected6 = true;
	  boolean actual6 = feed.contains(twt2);

	  int expected7 = 2;
	  int actual7 = feed.indexOf(twt3);

	  Tweet expected8 = twt1;
	  Tweet actual8 = feed.getHead();

	  Tweet expected9 = twt3;
	  Tweet actual9 = feed.getTail();

	  return expected1 == actual1 && expected2 == actual2 && expected3.equals(
		  actual3) && expected4.equals(actual4) && expected5.equals(
		  actual5) && expected6 == actual6 && expected7 == actual7 && expected8 == actual8 && expected9 == actual9;
	}
  }

  public static boolean testDeleteTweet() {
	TwitterFeed feed = new TwitterFeed();
	Tweet twt1 = new Tweet(new User("voldemort"), "i am tom riddle");//
	Tweet twt2 = new Tweet(new User("harryP"), "i am the chosen one");
	Tweet twt3 = new Tweet(new User("dumbledoor"), "i am the principal");//
	Tweet twt4 = new Tweet(new User("hermoine.G"), "im not a piegon harry");
	Tweet twt5 = new Tweet(new User("ronny_W"), "Why spiders?");   //

	feed.addFirst(twt1);
	feed.addLast(twt5);
	feed.add(1, twt2);
	feed.add(2, twt3);
	feed.add(3, twt4);

	if (feed.size() != 5 && !feed.getHead().equals(twt1) && !feed.getTail().equals(twt5))
	  return false;
	{
	  Tweet expected1 = twt1;   //
	  Tweet actual1 = feed.delete(0);

	  Tweet expected2 = twt2;
	  Tweet actual2 = feed.getHead();

	  int expected3 = 4; //
	  int actual3 = feed.size();

	  Tweet expected4 = twt5; //
	  Tweet actual4 = feed.delete(3);

	  Tweet expected5 = twt4;//
	  Tweet actual5 = feed.getTail();

	  int expected6 = 3;  //
	  int actual6 = feed.size();

	  Tweet expected7 = twt3; //
	  Tweet actual7 = feed.delete(1);

	  int expected8 = 2;  //
	  int actual8 = feed.size();

	  Tweet expected9 = twt4;  //
	  Tweet actual9 = feed.get(1);

	  return expected1.equals(actual1) && expected2.equals(
		  actual2) && expected3 == actual3 && expected4.equals(actual4) && expected5.equals(
		  actual5) && expected6 == actual6 && expected7.equals(
		  actual7) && expected8 == actual8 && expected9.equals(actual9);

	}
  }

  public static boolean testChronoTwiterator() {
	TwitterFeed feed = new TwitterFeed();
	Tweet twt1 = new Tweet(new User("voldemort"), "i am tom riddle");
	Tweet twt2 = new Tweet(new User("harryP"), "i am the chosen one");
	Tweet twt3 = new Tweet(new User("dumbledoor"), "i am the principal");

	feed.addFirst(twt1);
	feed.add(1, twt2);
	feed.add(2, twt3);

	String expected = "";
	for (Tweet twt : feed) {
	  expected += twt.toString() + "\n";
	}
	String actual = twt1 + "\n" + twt2 + "\n" + twt3 + "\n";
	if (!expected.equals(actual))
	  return false;
	return true;
  }
  public static boolean testVerifiedTwiterator(){
	TwitterFeed feed = new TwitterFeed();
	feed.setMode(TimelineMode.VERIFIED_ONLY);

	User user2 = new User("harryP");
	User user3 = new User("dumbledoor");

	user2.verify();
	user3.verify();

	Tweet twt1 = new Tweet(new User("voldemort"), "i am tom riddle");
	Tweet twt2 = new Tweet(user2, "i am the chosen one");
	Tweet twt3 = new Tweet(user3, "i am the principal");
	Tweet twt4 = new Tweet(new User("hermoine.G"), "im not a piegon harry");

	feed.addFirst(twt1);
	feed.add(1, twt2);
	feed.add(2, twt3);
	feed.add(3,twt4);

	String expected = "";
	for (Tweet twt : feed) {
	  expected += twt.toString() + "\n";
	}
	String actual =  twt2 + "\n" + twt3 + "\n";
	if (!expected.equals(actual))
	  return false;
	return true;
  }
  public static boolean testRatioTwiterator(){
	TwitterFeed feed = new TwitterFeed();
	feed.setMode(TimelineMode.LIKE_RATIO);
	Tweet twt1 = new Tweet(new User("voldemort"), "i am tom riddle");
	Tweet twt2 = new Tweet(new User("harryP"), "i am the chosen one");
	Tweet twt3 = new Tweet(new User("dumbledoor"), "i am the principal");
	Tweet twt4 = new Tweet(new User("hermoine.G"), "im not a piegon harry");

	twt1.like();
	twt1.like();

	twt2.retweet();
	twt2.like();
	twt2.like();

	feed.addFirst(twt1);
	feed.add(1, twt2);
	feed.add(2, twt3);
	feed.add(3,twt4);

	String expected = "";
	for (Tweet twt : feed) {
	  expected += twt.toString() + "\n";
	}
	String actual =  twt1 + "\n" + twt2 + "\n";
	if (!expected.equals(actual))
	  return false;
	return true;
  }

  public static void main(String[] args) {
	System.out.println("testUser() : " + testUser());
	System.out.println("testTweet() : " + testTweet());
	System.out.println("testNode() : " + testNode());
	System.out.println("testAddTweet() : " + testAddTweet());
	System.out.println("testInsertTweet() : " + testInsertTweet());
	System.out.println("testDeleteTweet() : " + testDeleteTweet());
	System.out.println("testChronoTwiterator() : " + testChronoTwiterator());
	System.out.println("testVerifiedTwiterator() : " + testVerifiedTwiterator());
	System.out.println("testRatioTwiterator() : " + testRatioTwiterator());

  }
}
