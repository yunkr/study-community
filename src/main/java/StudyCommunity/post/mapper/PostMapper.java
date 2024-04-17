package StudyCommunity.post.mapper;

import StudyCommunity.post.dto.PostPatchDto;
import StudyCommunity.post.dto.PostPostDto;
import StudyCommunity.post.dto.PostResponseDto;
import StudyCommunity.post.entity.Post;
import StudyCommunity.member.entity.Member;
import StudyCommunity.post.entity.PostTag;
import StudyCommunity.tag.dto.TagResponseDto;
import StudyCommunity.tag.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface PostMapper {

    default Post postPostDtoToPost(PostPostDto postPostDto) {

        Member member = new Member();
        Post post = new Post();

        member.setMemberId(member.getMemberId());

        post.setMember(postPostDto.getMember());
        post.setTitle(postPostDto.getTitle());
        post.setContent(postPostDto.getContent());
        post.setPostStatus(postPostDto.getPostStatus());

        if (postPostDto.getTags() != null) {
            for (String tag : postPostDto.getTags()) {
                Tag tagName = new Tag();
                tagName.setTagName(tag);
                PostTag postTag = new PostTag();
                postTag.setPost(post);
                postTag.setTag(tagName);
                post.getPostTags().add(postTag);
            }
        }

        return post;
    }

    default Post postPatchDtoToPost(PostPatchDto postPatchDto) {

        Post post = new Post();

        post.setPostId(postPatchDto.getPostId());

        post.setTitle(postPatchDto.getTitle());
        post.setContent(postPatchDto.getContent());

        return post;
    }

    default PostResponseDto postToPostResponseDto(Post post) {

        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setMemberId(post.getMember().getMemberId());

        postResponseDto.setPostId(post.getPostId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setPostStatus(post.getPostStatus());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setLastModifiedAt(post.getLastModifiedAt());

        postResponseDto.setTags(post.getPostTags()
                .stream()
                .map(postHashTag -> postHashTag.getTag().getTagName())
                .collect(Collectors.toSet()));

        return postResponseDto;

    }

    List<PostResponseDto> postsToPostResponseDtos(List<Post> posts);


//    default Set<String> tagSet(Set<PostTag> postHashTags) {
//        return postHashTags.stream()
//                .map(postHashTag -> postHashTag.getTag().getTagName())
//                .collect(Collectors.toSet());
//    }
}
