package io.jutil.coreservice.admin.fetcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Jin Zheng
 * @since 2025-09-08
 */
@Slf4j
@Component
public class OperatorFetcher {
	@Autowired(required = false)
	AccountFetcherFacade accountFetcherFacade;

	private final BiConsumer<AccountFetcherResponse, OperatorFetcherEntity> operatorFetchHandler =
			(r, e) -> {
				e.setOperatorCode(r.getCode());
				e.setOperatorName(r.getName());
			};

	public <T extends OperatorFetcherEntity> void fetchOperator(long tenantId, T entity) {
		this.fetch(entity, tenantId, operatorFetchHandler);
	}

	public void fetchOperator(long tenantId, Collection<? extends OperatorFetcherEntity> list) {
		this.fetch(list, tenantId, operatorFetchHandler);
	}

	private <T extends OperatorFetcherEntity> void fetch(T entity, long tenantId,
	                                                     BiConsumer<AccountFetcherResponse, OperatorFetcherEntity> f) {
		if (entity == null || entity.getOperatorId() <= 0L) {
			return;
		}
		var account = accountFetcherFacade.getOne(tenantId, entity.getOperatorId());
		if (account == null) {
			return;
		}
		f.accept(account, entity);
	}

	private void fetch(Collection<? extends OperatorFetcherEntity> list, long tenantId,
	                   BiConsumer<AccountFetcherResponse, OperatorFetcherEntity> f) {
		if (list == null || list.isEmpty()) {
			return;
		}
		var idList = list.stream()
				.map(OperatorFetcherEntity::getOperatorId)
				.filter(id -> id > 0)
				.toList();
		if (idList.isEmpty()) {
			return;
		}
		var accountMap = accountFetcherFacade.getList(tenantId, idList);
		if (accountMap == null || accountMap.isEmpty()) {
			return;
		}
		for (var entity : list) {
			var account = accountMap.get(entity.getOperatorId());
			if (account == null) {
				continue;
			}
			f.accept(account, entity);
		}
	}

	public interface AccountFetcherFacade {
		AccountFetcherResponse getOne(long tenantId, long accountId);
		Map<Long, ? extends AccountFetcherResponse> getList(long tenantId, Collection<Long> idList);
	}

	public interface AccountFetcherResponse {
		long getId();
		String getCode();
		String getName();
	}

	public interface OperatorFetcherEntity {
		long getOperatorId();
		void setOperatorCode(String operatorCode);
		void setOperatorName(String operatorName);
	}
}
