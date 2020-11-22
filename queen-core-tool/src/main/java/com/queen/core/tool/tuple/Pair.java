package com.queen.core.tool.tuple;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * tuple Pair
 *
 * @author jensen
 **/
@Getter
@ToString
@EqualsAndHashCode
public class Pair<L, R> {
	private static final Pair<Object, Object> EMPTY = new Pair<>(null, null);

	private final L left;
	private final R right;

	/**
	 * Returns an empty pair.
	 */
	@SuppressWarnings("unchecked")
	public static <L, R> Pair<L, R> empty() {
		return (Pair<L, R>) EMPTY;
	}

	/**
	 * Constructs a pair with its left value being {@code left}, or returns an empty pair if
	 * {@code left} is null.
	 *
	 * @return the constructed pair or an empty pair if {@code left} is null.
	 */
	public static <L, R> Pair<L, R> createLeft(L left) {
		if (left == null) {
			return empty();
		} else {
			return new Pair<>(left, null);
		}
	}

	/**
	 * Constructs a pair with its right value being {@code right}, or returns an empty pair if
	 * {@code right} is null.
	 *
	 * @return the constructed pair or an empty pair if {@code right} is null.
	 */
	public static <L, R> Pair<L, R> createRight(R right) {
		if (right == null) {
			return empty();
		} else {
			return new Pair<>(null, right);
		}
	}

	public static <L, R> Pair<L, R> create(L left, R right) {
		if (right == null && left == null) {
			return empty();
		} else {
			return new Pair<>(left, right);
		}
	}

	private Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

}
