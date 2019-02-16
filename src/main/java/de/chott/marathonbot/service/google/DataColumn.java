package de.chott.marathonbot.service.google;

import java.util.function.Function;

public enum DataColumn {

	A(0), B(1), C(2), D(3), E(4), F(5), G(6), H(7), I(8), J(9), K(10), L(11), M(12), N(13), O(14);

	private int index;

	private DataColumn(int index) {
		this.index = index;
	}

	public String get(String[] row) {
		return row[index].trim();
	}

	public Function<String[], String> get() {
		return this::get;
	}
}
