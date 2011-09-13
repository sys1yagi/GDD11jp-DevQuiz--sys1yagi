package devquiz2011.sys1yagi.challenge.bfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;


@SuppressWarnings("serial")
public class Stacks implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8541001762409826365L;
	public static boolean isSave = true;
	public static class Item implements Serializable{
		int zero;
		char[] work;
		Stack<Node> stack;
		int score;
	}
	long count = 0;
	ArrayList<Item> items = new ArrayList<Stacks.Item>();
	int size = 0;
	private final static Comparator<Item> SORTER = new Comparator<Stacks.Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.score-o2.score;
		}
	};
	public Item getNext(){
		Collections.sort(items, SORTER);
		for(int i = 0; i < size; i++){
			Item item = items.get(i);
			if(!item.stack.isEmpty()){
				return item;
			}
		}
		return null;
	}
	
	public void addItem(Item item){
		items.add(item);
		size = items.size();
	}
	
	
	public static Stacks loadCache(String line){
		if(!isSave){
			return null;
		}
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try{
			fis = new FileInputStream("files/cache/"+name(line));
			ois = new ObjectInputStream(fis);
			Stacks stacks = (Stacks)ois.readObject();
			return stacks;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Util.close(ois);
			Util.close(fis);			
		}
		return null;
	}
	public static void saveCache(String line, Stacks stacks){
		if(!isSave){
			return;
		}

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try{
			fos = new FileOutputStream("files/cache/"+name(line));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(stacks);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			Util.close(oos);
			Util.close(fos);			
		}
	}
	public static void clearCache(String line){
		if(!isSave){
			return;
		}
		System.out.println("cache clear:" + name(line));
		File file = new File("files/cache/" + name(line));
		file.delete();
	}
	public static String name(String line){
		return Long.toString(line.hashCode());
	}
	
}
