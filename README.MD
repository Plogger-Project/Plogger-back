src
└── main
    ├── java
    │   └── com
    │       └── project
    │           └── plogger
    │               ├── PloggerApplication.java
    │               ├── common
    │               │   ├── object
    │               │   │   ├── ActiveComment.java
    │               │   │   ├── ActivePost.java
    │               │   │   ├── ActiveReport.java
    │               │   │   ├── AddressPostCount.java
    │               │   │   ├── Alert.java
    │               │   │   ├── ChatMessage.java
    │               │   │   ├── ChatRoom.java
    │               │   │   ├── CityCount.java
    │               │   │   ├── CustomOAuth2User.java
    │               │   │   ├── Followee.java
    │               │   │   ├── Follower.java
    │               │   │   ├── Gifticon.java
    │               │   │   ├── InviteUser.java
    │               │   │   ├── JoinRoom.java
    │               │   │   ├── LeaveRoom.java
    │               │   │   ├── Mileage.java
    │               │   │   ├── MyRecruit.java
    │               │   │   ├── QnA.java
    │               │   │   ├── QnAComment.java
    │               │   │   ├── Recruit.java
    │               │   │   ├── RecruitComment.java
    │               │   │   ├── RecruitJoin.java
    │               │   │   ├── RecruitLike.java
    │               │   │   ├── RecruitReport.java
    │               │   │   ├── RoomInvite.java
    │               │   │   ├── Scrap.java
    │               │   │   ├── SimpleUser.java
    │               │   │   └── User.java
    │               │   └── util
    │               │       └── CreateNumber.java
    │               ├── config
    │               │   ├── SocketIOConfig.java
    │               │   ├── SocketIOServerRunner.java
    │               │   ├── SocketModule.java
    │               │   └── WebSecurityConfig.java
    │               ├── controller
    │               │   ├── ActiveLikeController.java
    │               │   ├── ActivePostController.java
    │               │   ├── ActiveReportController.java
    │               │   ├── ActiveTagController.java
    │               │   ├── AdminController.java
    │               │   ├── AlertController.java
    │               │   ├── AuthController.java
    │               │   ├── ChatController.java
    │               │   ├── FileController.java
    │               │   ├── FollowController.java
    │               │   ├── GifticonController.java
    │               │   ├── MileageController.java
    │               │   ├── QnAController.java
    │               │   ├── RecruitController.java
    │               │   ├── RecruitJoinController.java
    │               │   ├── RecruitLikeController.java
    │               │   ├── RecruitReportController.java
    │               │   ├── RecruitScrapController.java
    │               │   └── UserController.java
    │               ├── dto
    │               │   ├── MileageDownDto.java
    │               │   ├── MileageTagRemoveDto.java
    │               │   ├── MileageUpDto.java
    │               │   ├── request
    │               │   │   ├── active
    │               │   │   │   ├── ActiveReportRequestDto.java
    │               │   │   │   ├── PatchActiveCommentRequestDto.java
    │               │   │   │   ├── PatchActivePostRequestDto.java
    │               │   │   │   ├── PostActiveCommentRequestDto.java
    │               │   │   │   ├── PostActivePostRequestDto.java
    │               │   │   │   └── PostActiveTagRequestDto.java
    │               │   │   ├── alert
    │               │   │   │   └── AlertRequestDto.java
    │               │   │   ├── auth
    │               │   │   │   ├── FindIdAuthCheckDto.java
    │               │   │   │   ├── FindIdRequestDto.java
    │               │   │   │   ├── FindPasswordCheckDto.java
    │               │   │   │   ├── FindPasswordRequestDto.java
    │               │   │   │   ├── IdCheckRequestDto.java
    │               │   │   │   ├── SignInRequestDto.java
    │               │   │   │   ├── SignUpRequestDto.java
    │               │   │   │   ├── TelAuthCheckRequestDto.java
    │               │   │   │   └── TelAuthRequestDto.java
    │               │   │   ├── chat
    │               │   │   │   ├── PostChatMessageRequestDto.java
    │               │   │   │   └── PostChatRoomRequestDto.java
    │               │   │   ├── follow
    │               │   │   │   └── PostFollowRequestDto.java
    │               │   │   ├── gifticon
    │               │   │   │   ├── PatchGifticonRequestDto.java
    │               │   │   │   └── PostGifticonRequestDto.java
    │               │   │   ├── qna
    │               │   │   │   ├── PatchQnACommentRequestDto.java
    │               │   │   │   ├── PatchQnARequestDto.java
    │               │   │   │   ├── PostQnACommentRequestDto.java
    │               │   │   │   └── PostQnARequestDto.java
    │               │   │   ├── recruit
    │               │   │   │   ├── PatchRecruitCommentRequestDto.java
    │               │   │   │   ├── PatchRecruitIsCompletedRequestDto.java
    │               │   │   │   ├── PatchRecruitRequestDto.java
    │               │   │   │   ├── PostRecruitCommentRequestDto.java
    │               │   │   │   ├── PostRecruitRequestDto.java
    │               │   │   │   └── RecruitReportRequestDto.java
    │               │   │   └── user
    │               │   │       ├── CommentRequestDto.java
    │               │   │       ├── PatchPasswordRequestDto.java
    │               │   │       ├── PatchTelAuthRequestDto.java
    │               │   │       └── PatchUserRequestDto.java
    │               │   └── response
    │               │       ├── ResponseCode.java
    │               │       ├── ResponseDto.java
    │               │       ├── ResponseMessage.java
    │               │       ├── active
    │               │       │   ├── GetActiveCommentListResponseDto.java
    │               │       │   ├── GetActivePostListResponseDto.java
    │               │       │   ├── GetActivePostResponseDto.java
    │               │       │   ├── GetActivePostResultSet.java
    │               │       │   ├── GetActiveReportListResponseDto.java
    │               │       │   └── GetMyRecruitPostListResponseDto.java
    │               │       ├── admin
    │               │       │   ├── GetSignInResponseDto.java
    │               │       │   ├── GetUserListResponseDto.java
    │               │       │   └── GetUserResponseDto.java
    │               │       ├── alert
    │               │       │   ├── AlertResponseDto.java
    │               │       │   └── GetAlertListResponseDto.java
    │               │       ├── auth
    │               │       │   ├── FindIdResponseDto.java
    │               │       │   ├── FindPasswordResponseDto.java
    │               │       │   └── SignInResponseDto.java
    │               │       ├── chat
    │               │       │   ├── GetMessageListResponseDto.java
    │               │       │   ├── GetRoomListResponseDto.java
    │               │       │   └── GetRoomResponseDto.java
    │               │       ├── follow
    │               │       │   ├── GetFolloweeListResponseDto.java
    │               │       │   └── GetFollowerListResponseDto.java
    │               │       ├── gifticon
    │               │       │   ├── GetGifticonListResponseDto.java
    │               │       │   └── GetGifticonResponseDto.java
    │               │       ├── like
    │               │       │   ├── GetActiveLikeResponseDto.java
    │               │       │   └── GetRecruitLikeResponseDto.java
    │               │       ├── mileage
    │               │       │   └── GetMileageListResponseDto.java
    │               │       ├── qna
    │               │       │   ├── GetQnACommentListResponseDto.java
    │               │       │   ├── GetQnAListResponseDto.java
    │               │       │   └── GetQnAResponseDto.java
    │               │       ├── recruit
    │               │       │   ├── GetRecruitCityCountResponseDto.java
    │               │       │   ├── GetRecruitCommentListResponseDto.java
    │               │       │   ├── GetRecruitJoinListResponseDto.java
    │               │       │   ├── GetRecruitListResponseDto.java
    │               │       │   ├── GetRecruitReportListResponseDto.java
    │               │       │   ├── GetRecruitResponseDto.java
    │               │       │   └── RecruitReportDto.java
    │               │       └── scrap
    │               │           ├── GetRecruitScrapListResponseDto.java
    │               │           └── GetRecruitScrapResponseDto.java
    │               ├── entity
    │               │   ├── ActiveCommentEntity.java
    │               │   ├── ActiveLikeEntity.java
    │               │   ├── ActivePostEntity.java
    │               │   ├── ActiveReportEntity.java
    │               │   ├── ActiveTagEntity.java
    │               │   ├── FollowEntity.java
    │               │   ├── GifticonEntity.java
    │               │   ├── MileageEntity.java
    │               │   ├── QnACommentEntity.java
    │               │   ├── QnAEntity.java
    │               │   ├── RecruitCommentEntity.java
    │               │   ├── RecruitEntity.java
    │               │   ├── RecruitJoinEntity.java
    │               │   ├── RecruitLikeEntity.java
    │               │   ├── RecruitReportEntity.java
    │               │   ├── RecruitScrapEntity.java
    │               │   ├── TelAuthEntity.java
    │               │   ├── UserEntity.java
    │               │   ├── alert
    │               │   │   └── AlertEntity.java
    │               │   ├── chat
    │               │   │   ├── ChatJoinEntity.java
    │               │   │   ├── ChatMessageEntity.java
    │               │   │   ├── ChatReadEntity.java
    │               │   │   └── ChatRoomEntity.java
    │               │   └── pk
    │               │       ├── ActiveLikePk.java
    │               │       ├── ActiveTagPk.java
    │               │       ├── ChatJoinPk.java
    │               │       ├── ChatReadPk.java
    │               │       ├── RecruitJoinPk.java
    │               │       ├── RecruitLikePk.java
    │               │       └── RecruitScrapPk.java
    │               ├── filter
    │               │   └── JwtAuthenticationFilter.java
    │               ├── handler
    │               │   └── OAuth2SuccessHandler.java
    │               ├── provider
    │               │   ├── JwtProvider.java
    │               │   └── SmsProvider.java
    │               ├── repository
    │               │   ├── ActiveCommentRepository.java
    │               │   ├── ActiveLikeRepository.java
    │               │   ├── ActivePostRepository.java
    │               │   ├── ActiveReportRepository.java
    │               │   ├── ActiveTagRepository.java
    │               │   ├── AlertRepository.java
    │               │   ├── FollowRepository.java
    │               │   ├── GifticonRepository.java
    │               │   ├── MileageRepository.java
    │               │   ├── QnACommentRepository.java
    │               │   ├── QnARepository.java
    │               │   ├── RecruitCommentRepository.java
    │               │   ├── RecruitJoinRepository.java
    │               │   ├── RecruitLikeRepository.java
    │               │   ├── RecruitReportRepository.java
    │               │   ├── RecruitRepository.java
    │               │   ├── RecruitScrapRepository.java
    │               │   ├── TelAuthRepository.java
    │               │   ├── UserRepository.java
    │               │   ├── chat
    │               │   │   ├── ChatJoinRepository.java
    │               │   │   ├── ChatMessageRepository.java
    │               │   │   ├── ChatReadRepository.java
    │               │   │   └── ChatRoomRepository.java
    │               │   └── resultset
    │               │       ├── CityPostCountResultSet.java
    │               │       ├── GetChatMessageResultSet.java
    │               │       └── GetRecruitResultSet.java
    │               └── service
    │                   ├── ActiveCommentService.java
    │                   ├── ActiveLikeService.java
    │                   ├── ActivePostService.java
    │                   ├── ActiveReportService.java
    │                   ├── ActiveTagService.java
    │                   ├── AdminService.java
    │                   ├── AlertService.java
    │                   ├── AuthService.java
    │                   ├── ChatService.java
    │                   ├── FileService.java
    │                   ├── FollowService.java
    │                   ├── GifticonService.java
    │                   ├── Implement
    │                   │   ├── ActiveCommentServiceImplement.java
    │                   │   ├── ActiveLikeServiceImplement.java
    │                   │   ├── ActivePostServiceImplement.java
    │                   │   ├── ActiveReportServiceImplement.java
    │                   │   ├── ActiveTagServiceImplement.java
    │                   │   ├── AdminServiceImplement.java
    │                   │   ├── AlertImplement.java
    │                   │   ├── AuthServiceImplement.java
    │                   │   ├── ChatServiceImplement.java
    │                   │   ├── FileServiceImplement.java
    │                   │   ├── FollowServiceImplement.java
    │                   │   ├── GifticonServiceImplement.java
    │                   │   ├── MileageServiceImplement.java
    │                   │   ├── OAuth2UserServiceImplement.java
    │                   │   ├── QnACommentServiceImplement.java
    │                   │   ├── QnAServiceImplement.java
    │                   │   ├── RecruitCommentServiceImplement.java
    │                   │   ├── RecruitJoinServiceImplement.java
    │                   │   ├── RecruitLikeServiceImplement.java
    │                   │   ├── RecruitReportServiceImplement.java
    │                   │   ├── RecruitScrapServiceImplement.java
    │                   │   ├── RecruitServiceImplement.java
    │                   │   └── UserServiceImplement.java
    │                   ├── MileageService.java
    │                   ├── QnACommentService.java
    │                   ├── QnAService.java
    │                   ├── RecruitCommentService.java
    │                   ├── RecruitJoinService.java
    │                   ├── RecruitLikeService.java
    │                   ├── RecruitReportService.java
    │                   ├── RecruitScrapService.java
    │                   ├── RecruitService.java
    │                   └── UserService.java
    └── resources
        └── application.properties