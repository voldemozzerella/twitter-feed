import java.util.Iterator;
import java.util.NoSuchElementException;

public class ChronoTwiterator implements Iterator<Tweet> {
  private TweetNode next;

  public ChronoTwiterator(TweetNode startNode) {
	this.next = startNode;
  }

  @Override
  public boolean hasNext() {
	return next != null;
  }

  @Override
  public Tweet next() throws NoSuchElementException {
	if (!hasNext())
	  throw new NoSuchElementException("there is not a next tweet to return");
	Tweet value = next.getTweet();
	next = next.getNext();
	return value;
  }
}
