package structures;
/* a class which implements a basic tuple class. Behavior if used for hashing is unknown
 */

public class Pair<K, V> {
	
	private K key;
	private V value;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object o) {
		@SuppressWarnings("unchecked")
		Pair<K, V> pair = (Pair<K, V>) o;
		if(!key.equals(pair.getKey())) {
			return false;
		}
		if(!value.equals(pair.getValue())) {
			return false;
		}
		return true;
	}
	
}
