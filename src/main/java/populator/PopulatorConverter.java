package populator;

import java.util.ArrayList;
import java.util.List;

public class PopulatorConverter<S, T> {

	private Populator<S, T> populator;
	private Class<T> targetType;

	public PopulatorConverter(Populator<S, T> populator, Class<T> targetType) {
		this.populator = populator;
		this.targetType = targetType;
	}

	private T createFromClass() {
		try {
			return targetType.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public T convert(S source) {
		T target = createFromClass();
		populator.populate(source, target);
		return target;
	}

	public List<T> convertAll(List<S> sourceList) {
		List<T> targetList = new ArrayList<>();
		for (S source : sourceList) {
			targetList.add(convert(source));
		}
		return targetList;
	}
}
