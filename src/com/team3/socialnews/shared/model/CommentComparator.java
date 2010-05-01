package com.team3.socialnews.shared.model;

import java.util.Comparator;

/**
 * Sorts comments according to their total votes
 * and then according to their date.
 */
public class CommentComparator<C extends Comment> implements Comparator<C> {
	@Override
	public int compare(C firstComment, C secondComment) {
		if (firstComment.getVoteTotal() != secondComment.getVoteTotal())
			return secondComment.getVoteTotal() - firstComment.getVoteTotal();
		// equal in vote, now make the oldest first
		Long firstCommmentTime = firstComment.getCommentDate().getTime();
		Long secondCommentTime = secondComment.getCommentDate().getTime();
		return (int)(firstCommmentTime - secondCommentTime);
	}
}