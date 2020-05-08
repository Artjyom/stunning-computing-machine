package servpack;

import java.util.ArrayList;

public class ChatBox {
	private ArrayList<String> box;
	private int size;

	public ChatBox() {
		size = 20;
		box = new ArrayList<String>();
	}

	public void add(String a, String b) {
		if (!a.equals("")) {
			if (box.size() >= size) {
				for (int i = 1; i < box.size(); i++)
					box.set(i - 1, box.get(i));
				box.set(box.size(), a + " |" + b + "|");
			} else
				box.add(a + " |" + b + "|");
		}
	}

	public String get() {
		String temp = "";
		for (String s : box) {
			temp += s + "\n";
		}
		return temp;
	}
}
