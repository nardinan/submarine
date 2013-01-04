// Some math routines borrowed from the VRML implementation.

import java.lang.*;
import java.util.*;
import VbVec3f;

public class VbRotation {
  VbRotation() {
    quat = new float[4];
  }

  VbRotation(float q0, float q1, float q2, float q3) {
    quat = new float[4];
    quat[0] = q0; quat[1] = q1; quat[2] = q2; quat[3] = q3;
    normalize();
  }

  VbRotation(VbVec3f axis, float radians) {
    quat = new float[4];
    setValue(axis, radians);
  }

  public VbRotation setValue(VbVec3f axis, float radians) {
    VbVec3f q = new VbVec3f(axis);

    q.normalize();
    q.multBy((float) Math.sin(radians / 2.0f));

    float[] val = q.getValue();
    quat[0] = val[0];
    quat[1] = val[1];
    quat[2] = val[2];

    quat[3] = (float) Math.cos(radians / 2.0f);

    return this;
  }

  public float getValue(VbVec3f axis) {
    float	len;
    VbVec3f q = new VbVec3f(quat[0], quat[1], quat[2]);

    if ((len = q.length()) > 0.00001f) {
        q.multBy(1.0f / len);
	float[] val = q.getValue();
	axis.setValue(val[0], val[1], val[2]);
	return (float)(2.0 * Math.acos(quat[3]));
    }

    else {
	axis.setValue(0.0f, 0.0f, 1.0f);
	return 0.0f;
    }
  }

  VbRotation times(VbRotation q2) {
    VbRotation q = new VbRotation(q2.quat[3] * quat[0] + q2.quat[0] * quat[3] +
				  q2.quat[1] * quat[2] - q2.quat[2] * quat[1],

				  q2.quat[3] * quat[1] + q2.quat[1] * quat[3] +
				  q2.quat[2] * quat[0] - q2.quat[0] * quat[2],

				  q2.quat[3] * quat[2] + q2.quat[2] * quat[3] +
				  q2.quat[0] * quat[1] - q2.quat[1] * quat[0],

				  q2.quat[3] * quat[3] - q2.quat[0] * quat[0] -
				  q2.quat[1] * quat[1] - q2.quat[2] * quat[2]);
    q.normalize();
    return (q);
  }

  // Implementation details

  void normalize() {
    float	dist = (float)(1.0 / Math.sqrt(norm()));

    quat[0] *= dist;
    quat[1] *= dist;
    quat[2] *= dist;
    quat[3] *= dist;
  }

  float norm() {
    return (quat[0] * quat[0] +
	    quat[1] * quat[1] +
	    quat[2] * quat[2] +
	    quat[3] * quat[3]);
  }

  float[] quat;
}
