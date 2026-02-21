package itb.sdrank;

import java.util.List;

import itb.sdrank.exception.DiscoveryException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.DeviceDescription;

public interface DiscoveryEngine {

	List<Candidate> discover(Criteria criteria) throws DiscoveryException;

	void add(DeviceDescription deviceDescription) throws DiscoveryException;

	void add(List<DeviceDescription> deviceDescriptions) throws DiscoveryException;
	
	void clean() throws DiscoveryException;
	
	void setRepository(DescriptionStorage repository);
}
