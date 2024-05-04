package org.website.thienan.ricewaterthienan.mapper;

import org.springframework.stereotype.Component;
import org.website.thienan.ricewaterthienan.dto.request.RoleDetailRequest;
import org.website.thienan.ricewaterthienan.dto.response.RoleDetailResponse;
import org.website.thienan.ricewaterthienan.entities.RoleDetail;


@Component
public class RoleDetailMapper {
    public RoleDetail roleDetail(RoleDetailRequest roleDetailRequest){
        RoleDetail roleDetail = new RoleDetail();
        roleDetail.setName(roleDetailRequest.getName());
        roleDetail.setActive(true);
        return roleDetail;
    }

    public RoleDetailResponse roleDetailResponse(RoleDetail roleDetail){
        return RoleDetailResponse.builder()
                .id(roleDetail.getId())
                .name(roleDetail.getName())
                .active(roleDetail.getActive())
                .createAt(roleDetail.getCreateAt())
                .updateAt(roleDetail.getUpdateAt())
                .build();
    }

    public RoleDetail roleDetail(RoleDetailResponse roleDetailResponse){
        RoleDetail roleDetail = new RoleDetail();
        roleDetail.setId(roleDetailResponse.getId());
        roleDetail.setName(roleDetailResponse.getName());
        roleDetail.setCreateAt(roleDetailResponse.getCreateAt());
        roleDetail.setUpdateAt(roleDetailResponse.getUpdateAt());
        roleDetail.setActive(true);
        return roleDetail;
    }
}
