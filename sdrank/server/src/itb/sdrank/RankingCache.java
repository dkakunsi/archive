package itb.sdrank;

import java.util.List;

import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.Ranking;

public interface RankingCache extends Repository<Ranking> {
  List<Ranking> get(Criteria criteria) throws RepositoryException;

  List<Ranking> getByAttribute(String attribute) throws RepositoryException;
}
