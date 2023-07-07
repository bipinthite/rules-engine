package com.example.rules.api;

import static com.example.rules.api.RulesServiceEndpoints.ADMIN;
import static com.example.rules.api.RulesServiceEndpoints.API_BASE_URL;
import static com.example.rules.api.RulesServiceEndpoints.ENDPOINT_ADMIN_RELOAD_RULES;
import static com.example.rules.api.RulesServiceEndpoints.ENDPOINT_FIRE_RULE;
import static com.example.rules.api.RulesServiceEndpoints.PARAM_HEADER_KB;
import static com.example.rules.api.RulesServiceEndpoints.V1;

import com.example.rules.models.ApiResponse;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * RulesServiceClient.
 *
 * @author Bipin Thite
 */
@RetrofitClient(baseUrl = "${rules-service.address}")
public interface RulesServiceApi {

  /** Reload rules. */
  @POST(API_BASE_URL + V1 + ADMIN + ENDPOINT_ADMIN_RELOAD_RULES)
  Call<Void> reloadRules();

  /**
   * Fire rules.
   *
   * @param kb name of knowledge base
   * @param facts map of facts
   * @return map of results
   */
  @POST(API_BASE_URL + V1 + ENDPOINT_FIRE_RULE)
  ApiResponse<Map<String, Object>> fireRules(
      @Header(PARAM_HEADER_KB) String kb, @Body Map<String, Object> facts);
}
