package in.ashokit.binding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DashboardResponse {

	private Integer totalEnq;
	private Integer enrolledEnq;
	private Integer lostEnq;
}
