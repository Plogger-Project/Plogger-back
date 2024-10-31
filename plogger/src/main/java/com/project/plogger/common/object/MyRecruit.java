package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitJoinEntity;
import com.project.plogger.repository.RecruitJoinRepository;

import lombok.Getter;

@Getter
public class MyRecruit {
    private Integer recruitPostId;
    private String recruitPostTitle;
    private String recruitLocation;
    private List<String> recruitJoinPeople;

    public MyRecruit(RecruitEntity recruitEntity, RecruitJoinRepository joinRepository) {
        this.recruitPostId = recruitEntity.getRecruitPostId();
        this.recruitPostTitle = recruitEntity.getRecruitPostTitle();
        this.recruitLocation = recruitEntity.getRecruitLocation();
        this.recruitJoinPeople = new ArrayList<>();

        List<RecruitJoinEntity> joinEntities = joinRepository.findByRecruitId(recruitPostId);
        for (RecruitJoinEntity joinEntity : joinEntities) {
            this.recruitJoinPeople.add(joinEntity.getUserId());
        }
    }

    public static List<MyRecruit> getList(List<RecruitEntity> recruitEntities, RecruitJoinRepository joinRepository) {

        List<MyRecruit> recruits = new ArrayList<>();
        for (RecruitEntity recruitEntity : recruitEntities) {
            MyRecruit recruit = new MyRecruit(recruitEntity, joinRepository);
            recruits.add(recruit);
        }

        return recruits;
    }

}
