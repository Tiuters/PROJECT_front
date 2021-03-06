package com.warehouse_accounting.services.impl;

import com.warehouse_accounting.models.dto.LegalDetailDto;
import com.warehouse_accounting.services.interfaces.LegalDetailService;
import com.warehouse_accounting.services.interfaces.api.LegalDetailApi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class LegalDetailServiceImpl implements LegalDetailService {
    private final LegalDetailApi legalDetailApi;
    private final String legalDetailUrl;

    public LegalDetailServiceImpl(@Value("${retrofit.restServices.legal_detail_url}") String legalDetailUrl, Retrofit retrofit) {
        this.legalDetailUrl = legalDetailUrl;
        this.legalDetailApi = retrofit.create(LegalDetailApi.class);
    }

    @Override
    public List<LegalDetailDto> getAll() {
        List<LegalDetailDto> legalDetailDtoList = Collections.emptyList();
        Call<List<LegalDetailDto>> call = legalDetailApi.getAll(legalDetailUrl);
        try {
            Response<List<LegalDetailDto>> response = call.execute();
            if (response.isSuccessful()) {
                legalDetailDtoList = response.body();
                log.info("Успешно выполнен запрос на получение списка LegalDetailDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение списка LegalDetailDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на получение списка LegalDetailDto", e);
        }
        return legalDetailDtoList;
    }

    @Override
    public LegalDetailDto getById(Long id) {
        LegalDetailDto legalDetailDto = null;
        Call<LegalDetailDto> call = legalDetailApi.getById(legalDetailUrl, id);
        try {
            Response<LegalDetailDto> response = call.execute();
            if (response.isSuccessful()) {
                legalDetailDto = response.body();
                log.info("Успешно выполнен запрос на получение LegalDetailDto по id: {}", id);
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на получение LegalDetailDto по id: {}",response.code(), id);
            }
        } catch (Exception e) {
            log.error("Произошла ошибка при выполнении запроса на получение LegalDetailDto по id", e);
        }
        return legalDetailDto;
    }

    @Override
    public void create(LegalDetailDto dto) {
        Call<Void> call = legalDetailApi.create(legalDetailUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на создание LegalDetailDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на создании LegalDetailDto",response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на создании LegalDetailDto", e);
        }
    }

    @Override
    public void update(LegalDetailDto dto) {
        Call<Void> call = legalDetailApi.update(legalDetailUrl, dto);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на изменении LegalDetailDto");
            } else {
                log.error("Произошла ошибка {} при выполнении запроса на изменении LegalDetailDto", response.code());
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на изменении LegalDetailDto", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Call<Void> call = legalDetailApi.deleteById(legalDetailUrl, id);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                log.info("Успешно выполнен запрос на удаление LegalDetailDto");
            } else {
                log.error("Произошла ошибка при выполнении запроса на удаление LegalDetailDto по id: {}", id);
            }
        } catch (IOException e) {
            log.error("Произошла ошибка при выполнении запроса на удаление LegalDetailDto по id", e);
        }
    }
}