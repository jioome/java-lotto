package lotto.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum Rank {
	FIRST(6, 2_000_000_000, false),
	SECOND(5, 30_000_000, true),
	THIRD(5, 1_500_000, false),
	FOURTH(4, 50_000, false),
	FIFTH(3, 5_000, false),
	MISS(0, 0, false);

	private int matchCount;
	private int prize;
	private boolean isSecondBonus;

	Rank(int matchCount, int prize, boolean isSecondBonus) {
		this.matchCount = matchCount;
		this.prize = prize;
		this.isSecondBonus = isSecondBonus;
	}

	public static Rank from(int matchCount, boolean isBonus) {
		return Arrays.stream(Rank.values())
			.filter(rank -> rank.isSameMatchCount(matchCount))
			.filter(rank -> checkBonus(isBonus, rank))
			.findFirst()
			.orElse(MISS);
	}

	private static boolean checkBonus(boolean isBonus, Rank rank) {
		return isBonus || rank != SECOND;
	}

	private boolean isSameMatchCount(int matchCount) {
		return this.matchCount == matchCount;
	}

	public int countSameMatch(List<Rank> ranks) {
		return (int)ranks.stream()
			.filter(rank -> rank == this)
			.count();
	}

	public static List<Rank> availableRanks() {
		List<Rank> ranks = Arrays.stream(Rank.values())
			.filter(rank -> rank != MISS)
			.collect(Collectors.toList());
		Collections.reverse(ranks);
		return ranks;
	}

	public int getMatchCount() {
		return matchCount;
	}

	public int getPrize() {
		return prize;
	}

	public boolean isSecondBonus() {
		return isSecondBonus;
	}
}
