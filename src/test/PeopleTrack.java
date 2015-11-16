package test;

import java.util.Iterator;
import java.util.LinkedList;

import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Point;

class PeopleTrack {

	public static int countNewPersons(MatOfRect currentDetection,
			HeapList<MatOfRect> previousDetections, MatOfRect facesDetection) {

		int counter = 0;
		HeapList<LinkedList <Rect>> previousDetectionsClone = new HeapList<LinkedList <Rect>>(previousDetections.size());
		
		for (MatOfRect detection : previousDetections) {
			previousDetectionsClone.add(new LinkedList <Rect> (detection.toList()));
		}
		LinkedList <Rect> faceDetectionClone = new LinkedList<Rect>(facesDetection.toList());

		for (Rect person : currentDetection.toList()) {
			if (isNewPerson(person, previousDetectionsClone)) {
				if (personHasFace(person, faceDetectionClone)) {
					counter--;
				} else {
					counter++;
				}
			}
		}

		return counter;
	}

	public static boolean isNewPerson(Rect person,
			HeapList<LinkedList<Rect>> previousDetectionsClone) {
		boolean result = true;

		for (LinkedList<Rect> rectList : previousDetectionsClone) {
			
			Iterator<Rect> rectListIterator = rectList.iterator();
			Rect previousPerson = new Rect();
			
			while (rectListIterator.hasNext()) {
				previousPerson = rectListIterator.next();
				if (isNear(person, previousPerson, 1.5)) {
					result = false;					
					rectList.remove(previousPerson);
					break;
				}
			}
			
		}

		return result;
	}

	private static boolean isNear(Rect person, Rect previousPerson, double ratio) {
		Point personCenter = new Point(person.x + (person.width / 2), person.y
				+ (person.height / 2));
		Point previousCenter = new Point(previousPerson.x
				+ (previousPerson.width / 2), previousPerson.y
				+ (previousPerson.height / 2));

		Point maxAllowed = new Point(ratio
				* (person.width + previousPerson.width) / 2, ratio
				* (person.height + previousPerson.height) / 2);

		Point movement = new Point(previousCenter.x - personCenter.x,
				previousCenter.y - personCenter.y);

		if (movement.x < maxAllowed.x && movement.y < maxAllowed.y) {
			return true;
		}

		return false;
	}

	private static boolean personHasFace(Rect person, LinkedList<Rect> faceList) {
		double valsOrigin[] = { 0, 0 };
		double valsEnd[] = { 0, 0 };

		Point origin = new Point();
		Point end = new Point();

		Iterator<Rect> faceListIterator = faceList.iterator();
		Rect face = new Rect();
		
		while (faceListIterator.hasNext() ) {
			face  = faceListIterator.next();
			
			valsOrigin[0] = face.x;
			valsOrigin[1] = face.y;

			valsEnd[0] = face.x + face.width;
			valsEnd[1] = face.y + face.height;

			origin.set(valsOrigin);
			end.set(valsEnd);

			if (origin.inside(person) && end.inside(person)) {
				System.out.println("plop");
				faceList.remove(face);
			}

		}

		return false;
	}
}
