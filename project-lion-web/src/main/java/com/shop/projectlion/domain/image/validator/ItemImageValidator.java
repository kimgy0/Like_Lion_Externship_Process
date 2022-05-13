package com.shop.projectlion.domain.image.validator;

import com.shop.projectlion.infra.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemImageValidator {

    public boolean isFirstImagExist(List<String> originalNames, List<MultipartFile> multipartFiles, int FIRST_IMAGE_INDEX) {
        return originalNames.get(FIRST_IMAGE_INDEX).isBlank()
                && multipartFiles.get(FIRST_IMAGE_INDEX).isEmpty();
    }

    public boolean isDeleteImage(UploadFile uploadFile, String originalName) {
        return uploadFile.getOriginalFileName()==null && originalName.isEmpty();
    }

    public boolean isAddImage(UploadFile uploadFile, String originalName) {
        return uploadFile.getOriginalFileName()!=null && originalName.isEmpty();
    }

    public boolean isChangeImage(UploadFile uploadFile, String originalName) {
        return uploadFile.getOriginalFileName()!=null && !originalName.isEmpty();
    }

}
