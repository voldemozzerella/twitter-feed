public class TweetNode {
  //A container for a Tweet in a singly-linked list
  private Tweet tweet; //tweet contained in this node

  private TweetNode nextTweet; //next TweetNode in this linkedlist

  //constructor
  public TweetNode(Tweet tweet,
	  TweetNode next){
	this.tweet = tweet;
	this.nextTweet = next;
  }
  public TweetNode(Tweet tweet){
	this(tweet,null);
  }
  public Tweet getTweet(){
	return this.tweet;
  }
  public TweetNode getNext(){
	return this.nextTweet;
  }
  public void setNext(TweetNode next){
	this.nextTweet = next;
  }
}
