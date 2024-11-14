package com.project.plogger.service.Implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.chat.PostChatMessageRequestDto;
import com.project.plogger.dto.request.chat.PostChatRoomRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.chat.GetMessageListResponseDto;
import com.project.plogger.dto.response.chat.GetRoomListResponseDto;
import com.project.plogger.dto.response.chat.GetRoomResponseDto;
import com.project.plogger.entity.chat.ChatJoinEntity;
import com.project.plogger.entity.chat.ChatMessageEntity;
import com.project.plogger.entity.chat.ChatRoomEntity;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.repository.chat.ChatJoinRepository;
import com.project.plogger.repository.chat.ChatMessageRepository;
import com.project.plogger.repository.chat.ChatRoomRepository;
import com.project.plogger.repository.resultset.GetChatMessageResultSet;
import com.project.plogger.service.ChatService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImplement implements ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final UserRepository userRepository;

    // 채팅방 만들기
    @Override
    public ResponseEntity<ResponseDto> createChatRoom(PostChatRoomRequestDto dto, String userId) {

        try {

            ChatRoomEntity roomEntity = new ChatRoomEntity(dto);
            chatRoomRepository.save(roomEntity);

            ChatJoinEntity joinEntity = new ChatJoinEntity();
            joinEntity.setRoomId(roomEntity.getRoomId());
            joinEntity.setUserId(userId);
            joinEntity.setJoinedAt(LocalDateTime.now().toString());
            chatJoinRepository.save(joinEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    // 메세지 쓰기
    @Override
    public ResponseEntity<ResponseDto> saveMessage(PostChatMessageRequestDto dto, Integer roomId, String senderId) {

        try {

            boolean isExistedRoom = chatRoomRepository.existsByRoomId(roomId);
            if (!isExistedRoom) return ResponseDto.noExistChatRoom();

            boolean isExistedUser = userRepository.existsByUserId(senderId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            ChatMessageEntity chatMessageEntity = new ChatMessageEntity(dto, senderId, roomId);
            chatMessageRepository.save(chatMessageEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    // 메세지들 들고오기
    @Override
    public ResponseEntity<? super GetMessageListResponseDto> getMessages(Integer roomId, String userId) {

        List<GetChatMessageResultSet> resultSets = new ArrayList<>();
        
        try {

            boolean isExistedRoom = chatRoomRepository.existsByRoomId(roomId);
            if (!isExistedRoom) return ResponseDto.noExistChatRoom();

            resultSets = chatMessageRepository.getChatMessageList(userId, roomId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMessageListResponseDto.success(resultSets);

    }

    // 채팅방에 참여하기
    @Override
    public ResponseEntity<ResponseDto> joinRoom(Integer roomId, String userId) {
        
        try {

            boolean isExistedRoom = chatRoomRepository.existsByRoomId(roomId);
            if (!isExistedRoom) return ResponseDto.noExistChatRoom();

            boolean isExistedUser = userRepository.existsByUserId(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            ChatJoinEntity chatJoinEntity = new ChatJoinEntity(roomId, userId);
            chatJoinRepository.save(chatJoinEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }
    

    // 내가 참여한 채팅방 가지고오기
    @Override
    public ResponseEntity<? super GetRoomListResponseDto> getMyRooms(String userId) {

        List<ChatJoinEntity> joinEntities = new ArrayList<>();
        List<ChatRoomEntity> chatRoomEntities = new ArrayList<>();

        try {

            boolean isExistedUser = userRepository.existsByUserId(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            // 사용자가 참여하고 있는 채팅방 조회
            joinEntities = chatJoinRepository.findByUserId(userId);

            for (ChatJoinEntity joinEntity : joinEntities) {
                Integer roomId = joinEntity.getRoomId();
                ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(roomId);
                if (chatRoomEntity != null) {
                    chatRoomEntities.add(chatRoomEntity);
                }
            }

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRoomListResponseDto.success(chatRoomEntities);

    }

    @Override
    public ResponseEntity<? super GetRoomResponseDto> getRoom(Integer roomId) {

        ChatRoomEntity chatRoomEntity = null;

        try {

            chatRoomEntity = chatRoomRepository.findByRoomId(roomId);
            if (chatRoomEntity == null) return ResponseDto.noExistChatRoom();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRoomResponseDto.success(chatRoomEntity);

    }

    // 채팅방 나가기
    @Override
    public ResponseEntity<ResponseDto> leaveRoom(Integer roomId, String userId) {

        try {

            boolean isExistedUser = userRepository.existsByUserId(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            boolean isExistedRoom = chatRoomRepository.existsByRoomId(roomId);
            if (!isExistedRoom) return ResponseDto.noExistChatRoom();

            chatJoinRepository.deleteByRoomIdAndUserId(roomId, userId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetMessageListResponseDto> getTotalChatMessages(String userId) {
        List<GetChatMessageResultSet> resultSets = new ArrayList<>();
        
        try {

            resultSets = chatMessageRepository.getChatMessageList(userId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMessageListResponseDto.success(resultSets);
    }
    
}
