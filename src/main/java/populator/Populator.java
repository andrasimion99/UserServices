package populator;

public interface Populator<S, T> {
	void populate(S s, T t);
}
