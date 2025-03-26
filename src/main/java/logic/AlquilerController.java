package logic;

import data.AlquilerData;
import entities.Alquiler;

public class AlquilerController {

	private AlquilerData ad;

	public AlquilerController() {
		ad = new AlquilerData();
	}

	public void add(Alquiler alq) {
		ad.add(alq);
	}

	public void update(Alquiler alq) {
		ad.update(alq);
	}

	public void delete(Alquiler alq) {
		ad.delete(alq);
	}

}