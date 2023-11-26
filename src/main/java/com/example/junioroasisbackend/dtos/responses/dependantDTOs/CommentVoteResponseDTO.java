package com.example.junioroasisbackend.dtos.responses.dependantDTOs;

import com.example.junioroasisbackend.entities.depentantEntities.CommentVote;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class CommentVoteResponseDTO {
    private Long userId ;
    private Long commentId;

    public static  CommentVoteResponseDTO mapToDTO(CommentVote commentVote) {
        CommentVoteResponseDTO commentVoteResponseDTO = new CommentVoteResponseDTO();
        commentVoteResponseDTO.setCommentId(commentVote.getComment().getId());
        commentVoteResponseDTO.setUserId(commentVote.getUser().getId());
        return commentVoteResponseDTO;
    }

    public  static List<CommentVoteResponseDTO> mapToListDTO(List<CommentVote> commentVotes) {
        return commentVotes.stream().map(CommentVoteResponseDTO::mapToDTO).collect(Collectors.toList());
    }

}
