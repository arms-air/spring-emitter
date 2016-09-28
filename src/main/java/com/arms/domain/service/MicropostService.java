package com.arms.domain.service;

import com.arms.app.home.MicropostCreateForm;
import com.arms.domain.entity.Micropost;
import com.arms.domain.repository.MicropostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by arms_matsushita on 西暦2016/09/25.
 */
@Service
public class MicropostService extends AppService {

    @Autowired
    MicropostRepository micropostRepository;

    /**
     *
     * @param micropostCreateForm
     * @return
     */
    public int createMicropost(MicropostCreateForm micropostCreateForm) {
        Date nowDate = Calendar.getInstance().getTime();
        Micropost micropost = new Micropost();
        micropost.setContent(micropostCreateForm.getContent());
        micropost.setUserId(micropostCreateForm.getUserId());
        micropost.setCreated(nowDate);
        micropost.setUpdated(nowDate);
        micropostRepository.save(micropost);
        return micropost.getId();
    }

    /**
     *
     * @param micropostId
     */
    public void deleteMicropost(int micropostId) {
        micropostRepository.delete(micropostId);
    }
}
