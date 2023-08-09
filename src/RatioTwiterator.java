import java.util.Iterator;
import java.util.NoSuchElementException;

public class RatioTwiterator implements Iterator<Tweet> {
  private TweetNode next;
  private final double THRESHOLD;

  public RatioTwiterator(TweetNode startNode, double threshold) {
	THRESHOLD = threshold;
	while (startNode != null) {
	  if (startNode.getTweet().getLikesRatio() >= THRESHOLD) {
		this.next = startNode;
		break;
	  } else {
		startNode = startNode.getNext();
	  }
	}
  }

  @Override
  public boolean hasNext() {
	return next != null && next.getTweet().getLikesRatio() >= THRESHOLD;
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
