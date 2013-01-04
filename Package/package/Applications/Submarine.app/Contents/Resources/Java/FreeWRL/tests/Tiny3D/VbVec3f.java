// Some math routines borrowed from the VRML implementation.

import java.lang.*;

public class VbVec3f {

  VbVec3f() {
    vec = new float[3];
  }

  VbVec3f(VbVec3f arg) {
    vec = new float[3];
    vec[0] = arg.vec[0]; vec[1] = arg.vec[1]; vec[2] = arg.vec[2];
  }

  VbVec3f(float x, float y, float z) {
    vec = new float[3];
    setValue(x, y, z);
  }

  public float length() {
    return (float) Math.sqrt(vec[0] * vec[0] +
			     vec[1] * vec[1] +
			     vec[2] * vec[2]);
  }

  public void normalize() {
    float len = length();

    if (len != 0.0) {
      multBy(len);
    }

    else setValue(0.0f, 0.0f, 0.0f);
  }

  public void setValue(float x, float y, float z) {
    vec[0] = x; vec[1] = y; vec[2] = z;
  }

  float[] getValue() {
    return vec;
  }

  void multBy(float arg) {
    vec[0] *= arg;
    vec[1] *= arg;
    vec[2] *= arg;
  }

  VbVec3f times(float arg) {
    VbVec3f v = new VbVec3f();
    v.vec[0] = vec[0] * arg;
    v.vec[1] = vec[1] * arg;
    v.vec[2] = vec[2] * arg;
    return v;
  }

  // Implementation details
  float[] vec;
}
