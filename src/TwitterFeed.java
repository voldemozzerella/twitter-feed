import java.util.Iterator;
import java.util.NoSuchElementException;

public class TwitterFeed extends Object implements ListADT<Tweet>, Iterable<Tweet> {
  private TweetNode head;  //tweetnode of most recent tweet
  private TweetNode tail; //tweetnode of oldest tweet
  private int size; //number of tweets in this linked list
  private TimelineMode mode; //iteration mode for this timeline display
  private static double ratio; //ratio of likes to total engagement; 0.5 by default.

  public TwitterFeed() {
	this.head = null;
	this.tail = null;
	this.size = 0;
	this.mode = TimelineMode.CHRONOLOGICAL;
	ratio = 0.5;
  }

  @Override
  public int size() {
	return this.size;
  }

  @Override
  public boolean isEmpty() {
	return size == 0 && head == null && tail == null;
  }

  @Override
  public boolean contains(Tweet findObject) {
	TweetNode temp = head;
	for (int i = 0; i < size; i++) {
	  if (temp.getTweet().equals(findObject))
		return true;
	  else
		temp = temp.getNext();
	}
	return false;
  }

  public void setMode(TimelineMode m) {
	mode = m;
  }

  @Override
  public int indexOf(Tweet findObject) {
	TweetNode temp = head;
	for (int i = 0; i < size; i++) {
	  if (temp.getTweet().equals(findObject))
		return i;
	  temp = temp.getNext();
	}
	return -1;
  }

  public Tweet getHead() throws NoSuchElementException {
	if (isEmpty())
	  throw new NoSuchElementException("empty twitter feed.");
	else
	  return head.getTweet();
  }

  public Tweet getTail() throws NoSuchElementException {
	if (isEmpty())
	  throw new NoSuchElementException("empty twitter feed.");
	else
	  return tail.getTweet();
  }

  @Override
  public Tweet get(int index) {
	if (index > size - 1 || index < 0)
	  throw new IndexOutOfBoundsException(
		  "index is negative or greater than the largest index of the TwitterFeed");
	TweetNode newNode = head;
	for (int i = 0; i < index; i++)  //keep changing the twt node untill u reach desired index from
	  //start node
	  newNode = newNode.getNext();
	return newNode.getTweet();  //return the tweet in the node found.
  }

  @Override
  public void addLast(Tweet newObject) {
	if (this.isEmpty()) {
	  this.head = new TweetNode(newObject); //if theres only one twt, head and tail are the same
	  this.tail = this.head;
	} else {
	  TweetNode newNode = new TweetNode(newObject);
	  tail.setNext(newNode); // current tail's next twt should be the new twt
	  this.tail = newNode; //the new tweet now becomes the tail
	}
	size++;
  }

  @Override
  public void addFirst(Tweet newObject) {
	if (this.isEmpty()) {
	  this.head = new TweetNode(newObject); //if theres only one twt, head and tail are the same
	  this.tail = this.head;
	} else {
	  TweetNode newNode = new TweetNode(newObject);
	  newNode.setNext(this.head);
	  this.head = newNode;
	}
	size++;

  }

  @Override
  public void add(int index, Tweet newObject) {
	if (index > size  || index < 0)
	  throw new IndexOutOfBoundsException(
		  "index is negative or greater than the largest index of the TwitterFeed");
	if (this.isEmpty()) {
	  this.head = new TweetNode(newObject); //if theres only one twt, head and tail are the same
	  this.tail = this.head;
	}
	if (index == 0)
	  addFirst(newObject);
	if (index == size)
	  addLast(newObject);
	else {
	  TweetNode temp = head;
	  TweetNode added = new TweetNode(newObject);
	  for (int i = 0; i < index - 1; i++)
		temp = temp.getNext();
	  added.setNext(temp.getNext());  //new node's next is the index's next
	  temp.setNext(added);
	  size++;
	}
  }

  @Override
  public Tweet delete(int index) {
	if (index > size - 1 || index < 0)
	  throw new IndexOutOfBoundsException(
		  "index is negative or greater than the largest index of the TwitterFeed");
	Tweet removed;
	if (index == 0) {
	  removed = head.getTweet();
	  head = head.getNext();
	  if (head == null)
		tail = null;
	  size--;
	}
	//	if (index == size - 1) {
	//	  removed = tail.getTweet();
	//
	//	  tail.setNext(null);
	//	  size--;
	//	  return removed;
	//	}
	else {
	  TweetNode temp = head;
	  for (int i = 0; i < index - 1; i++)  //stops 1 before the index to be removed
		temp = temp.getNext();
	  if (index == size - 1) {
		removed = get(index);
		tail = temp;
	  } else {
		removed = temp.getNext().getTweet();
	  }
	  temp.setNext(temp.getNext().getNext());
	  size--;
	}
	return removed;
  }

  @Override
  public Iterator<Tweet> iterator() {
	if (mode == TimelineMode.CHRONOLOGICAL) {
	  return new ChronoTwiterator(this.head);
	}
	if (mode == TimelineMode.VERIFIED_ONLY){
	  return new VerifiedTwiterator(this.head);
	}
	else
	  return new RatioTwiterator(this.head,ratio);
  }
}
