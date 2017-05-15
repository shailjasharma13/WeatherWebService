package weather.boot;

import com.basho.riak.client.api.commands.kv.UpdateValue;

public final class UserUpdate extends UpdateValue.Update<User> {

	private final User update;

	public UserUpdate(User update) {
		this.update = update;
	}

	@Override
	public User apply(User t) {
		if (t == null) {
			t = new User();
		}

		t.setCityList(update.getCityList());
		t.setUserId(update.getUserId());
		t.setFirstName(update.getFirstName());
		t.setLastName(update.getLastName());
		t.setTemperatureUnit(update.getTemperatureUnit());

		return t;
	}
}
