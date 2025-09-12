package io.jutil.coreservice.admin.fetcher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin Zheng
 * @since 2025-09-08
 */
@ExtendWith(MockitoExtension.class)
class OperatorFetcherTest {
	@Spy
	Facade facade;

	@InjectMocks
	OperatorFetcher fetcher;

	final long tenantId = 0L;


	@Test
	void testFetchOperator() {
		var entity = new Entity(1L);
		fetcher.fetchOperator(tenantId, entity);
		Assertions.assertEquals("code1", entity.getOperatorCode());
		Assertions.assertEquals("name1", entity.getOperatorName());
		Mockito.verify(facade).getOne(Mockito.eq(tenantId), Mockito.eq(1L));
	}

	@Test
	void testFetchOperator1() {
		var entity = new Entity(0L);
		fetcher.fetchOperator(tenantId, entity);
		Assertions.assertNull(entity.getOperatorCode());
		Assertions.assertNull(entity.getOperatorName());
		Mockito.verify(facade, Mockito.never()).getOne(Mockito.anyLong(), Mockito.anyLong());
	}

	@Test
	void testFetchOperator2() {
		var entity = new Entity(100L);
		fetcher.fetchOperator(tenantId, entity);
		Assertions.assertNull(entity.getOperatorCode());
		Assertions.assertNull(entity.getOperatorName());
		Mockito.verify(facade).getOne(Mockito.eq(tenantId), Mockito.eq(100L));
	}

	@Test
	void testFetchListOperator() {
		List<Entity> entityList = new ArrayList<>();
		entityList.add(new Entity(1L));
		entityList.add(new Entity(2L));
		fetcher.fetchOperator(tenantId, entityList);
		Assertions.assertEquals(2, entityList.size());
		Assertions.assertEquals("code1", entityList.getFirst().getOperatorCode());
		Assertions.assertEquals("name1", entityList.getFirst().getOperatorName());
		Assertions.assertEquals("code2", entityList.get(1).getOperatorCode());
		Assertions.assertEquals("name2", entityList.get(1).getOperatorName());
		Mockito.verify(facade).getList(Mockito.eq(tenantId), Mockito.eq(List.of(1L, 2L)));
	}

	@Test
	void testFetchListOperator1() {
		List<Entity> entityList = new ArrayList<>();
		fetcher.fetchOperator(tenantId, entityList);
		Assertions.assertTrue(entityList.isEmpty());
		Mockito.verify(facade, Mockito.never()).getList(Mockito.anyLong(), Mockito.anyCollection());
	}

	@Test
	void testFetchListOperator2() {
		List<Entity> entityList = new ArrayList<>();
		entityList.add(new Entity(1L));
		entityList.add(new Entity(0L));
		fetcher.fetchOperator(tenantId, entityList);
		Assertions.assertEquals(2, entityList.size());
		Assertions.assertEquals("code1", entityList.getFirst().getOperatorCode());
		Assertions.assertEquals("name1", entityList.getFirst().getOperatorName());
		Assertions.assertNull(entityList.get(1).getOperatorCode());
		Assertions.assertNull(entityList.get(1).getOperatorName());
		Mockito.verify(facade).getList(Mockito.eq(tenantId), Mockito.eq(List.of(1L)));
	}

	@Test
	void testFetchListOperator3() {
		List<Entity> entityList = new ArrayList<>();
		entityList.add(new Entity(1L));
		entityList.add(new Entity(100L));
		fetcher.fetchOperator(tenantId, entityList);
		Assertions.assertEquals(2, entityList.size());
		Assertions.assertEquals("code1", entityList.getFirst().getOperatorCode());
		Assertions.assertEquals("name1", entityList.getFirst().getOperatorName());
		Assertions.assertNull(entityList.get(1).getOperatorCode());
		Assertions.assertNull(entityList.get(1).getOperatorName());
		Mockito.verify(facade).getList(Mockito.eq(tenantId), Mockito.eq(List.of(1L, 100L)));
	}

	@Test
	void testFetchListOperator4() {
		List<Entity> entityList = new ArrayList<>();
		entityList.add(new Entity(100L));
		fetcher.fetchOperator(tenantId, entityList);
		Assertions.assertEquals(1, entityList.size());
		Assertions.assertNull(entityList.getFirst().getOperatorCode());
		Assertions.assertNull(entityList.getFirst().getOperatorName());
		Mockito.verify(facade).getList(Mockito.eq(tenantId), Mockito.eq(List.of(100L)));
	}

	static class Facade implements OperatorFetcher.AccountFetcherFacade {

		@Override
		public Response getOne(long tenantId, long operatorId) {
			if (operatorId == 100L) {
				return null;
			}
			return new Response(operatorId, "code" + operatorId, "name" + operatorId);
		}

		@Override
		public Map<Long, ? extends OperatorFetcher.AccountFetcherResponse> getList(long tenantId, Collection<Long> operatorIdList) {
			Map<Long, Response> map = new HashMap<>();
			for (var operatorId : operatorIdList) {
				if (operatorId == 100L) {
					continue;
				}
				map.put(operatorId, new Response(operatorId, "code" + operatorId, "name" + operatorId));
			}
			return map;
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	static class Response implements OperatorFetcher.AccountFetcherResponse {
		private long id;
		private String code;
		private String name;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	static class Entity implements OperatorFetcher.OperatorFetcherEntity {
		private long operatorId;
		private String operatorCode;
		private String operatorName;

		public Entity(long operatorId) {
			this.operatorId = operatorId;
		}
	}
}
