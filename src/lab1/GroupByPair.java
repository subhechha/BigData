package lab1;


import java.util.List;

public class GroupByPair<K, V> {
	
	private K key; 
	private List<V> value;
	
	public GroupByPair(){
		
	}
	
	
	public GroupByPair(K key, List<V> value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public List<V> getValue() {
		return value;
	}
	
	public void setValue(List<V> value) {
		this.value = value;
	}
	
	public void setValue(V value){
		this.value.add(value);
	}
	
	public int checkContains(K key){
		System.out.println(this.key);
		return -1;
	}


	@Override
	public String toString() {
		return "GroupByPair [key=" + key + ", value=" + value + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupByPair other = (GroupByPair) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
	
}