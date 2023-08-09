import java.util.Iterator;
import java.util.NoSuchElementException;

public class VerifiedTwiterator implements Iterator<Tweet>{
  private TweetNode next;
  public VerifiedTwiterator(TweetNode startNode){
	while (startNode != null) {
	  if (startNode.getTweet().isUserVerified()) {
		this.next = startNode;
		break;
	  } else {
		startNode = startNode.getNext();
	  }
	}
  }
  @Override
  public boolean hasNext() {
	return next != null && next.getTweet().isUserVerified();
  }

  @Override
  public Tweet next() {
	if (next==null)
	  throw new NoSuchElementException("there is not a next tweet to return");
	Tweet value = next.getTweet();

	do {
	  next = next.getNext();
	} while (next != null && !next.getTweet().isUserVerified());  //you want to skip the user who is not verified
	return value;  }
}
