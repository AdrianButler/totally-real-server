package adrian.totallyrealserver.services;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SetUtils
{
	public <T> T searchSet(Set<T> set, T key) // iterate over set to see if key exists
	{
		for (T item : set)
		{
			if (item.equals(key))
			{
				return item;
			}
		}

		return null;
	}
}
