package manage;

public class QueryManage {

	private String query02 = "SELECT name FROM u168183796_qaloantec.cron_schedules limit 2";
	private String query03 = "select firstname from u168183796_qaloantec.users where email = 'test@gmail.com'";
	private String updateQuery01 = "update users set mobile = 88889999 where username like '%e_'";

	// ******* Getter ******


	public String getQuery02() {
		return query02;
	}

	public String getQuery03() {
		return query03;
	}

	public String getUpdateQuery01() {
		return updateQuery01;
	}
}
