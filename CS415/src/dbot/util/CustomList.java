package dbot.util;

import java.util.ArrayList;

public class CustomList extends ArrayList<Integer>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8179610740757900224L;
	private int min_index;
	
	public CustomList() {
		min_index = 0;
		calculateMin();
	}
	
	public void calculateMin() {
		if(this.size() <= 1) {
			return;
		}
		
		int min = this.get(0);
		
		for(int i = 1; i < this.size(); i++) {
			if(this.get(i) <= min) {
				min = this.get(i);
				min_index = i;
			}
		}
	}
	
	public int removeMin() {
		if(this.size() == 0) {
			return 0;
		}
		
		if(this.size() < 2) {
			return this.get(0);
		}
		
		calculateMin();
		if(min_index == this.size()) {
			min_index -= 1;
		}
		int dropped_min = this.get(min_index);
		this.remove(min_index);
		return dropped_min;
	}	
	
	public int getTotal() {
		int t = 0;
		
		if(this.size() == 0) {
			return t;
		} else {
			for(int i = 0; i < this.size(); i++) {
				t += this.get(i);
			}
			return t;
		}
	}
}
