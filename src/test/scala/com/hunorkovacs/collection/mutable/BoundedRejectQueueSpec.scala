package com.hunorkovacs.collection.mutable

import org.specs2.mutable.Specification

class BoundedRejectQueueSpec extends Specification {

  "size" should {
    "reflect the real size after adding." in {
      val n = 10
      val q = BoundedRejectQueue[Int](n)
      (1 to n).foreach(i => q.add(i))
      q.size must equalTo(n)
    }
    "reflect real size after reaching bound and still adding." in {
      val n = 10
      val q = BoundedRejectQueue[Int](n)
      (1 to 2 * n).foreach(i => q.add(i))
      q.size must equalTo(n)
    }
    "reflect real size after removing." in {
      val n = 10
      val q = BoundedRejectQueue[Int](n)
      (1 to n).foreach(i => q.add(i))
      (1 to n).foreach(_ => q.poll)
      q.size must equalTo(0)
    }
    "keep up with many instructions" in {
      val n = 30000
      val q = BoundedRejectQueue[Int](n)
      val k1 = 1000
      (1 to k1).foreach(i => q.add(i))
      val k2 = 500
      (1 to k2).foreach { i =>
        q.add(i)
        q.poll
        q.add(i)
      }
      val k3 = 20000
      (1 to k3).foreach(i => q.add(i))
      q.size must beEqualTo(k1 + k2 + k3)
      val k4 = 20000
      (1 to k4).foreach(i => q.add(i))
      q.size must beEqualTo(n)
      (1 to n - 1).foreach(_ => q.poll)
      q.size must beEqualTo(1)
      (1 to k1).foreach(_ => q.poll)
      q.size must beEqualTo(0)
    }
  }

  "Adding" should {
    "reject elements after bound reached." in {
      val n = 10
      val q = BoundedRejectQueue[Int](n)
      (1 to n) foreach { i =>
        q.add(i) must beTrue
      }
      (1 to n) foreach { i =>
        q.add(i) must beFalse
      }
      q.add(n + 1) must beFalse
    }
  }

  "Polling" should {
    "return None after emptied." in {
      val n = 10
      val q = BoundedRejectQueue[Int](n)
      (1 to n).foreach(i => q.add(i))
      (1 to n).foreach { i =>
        q.poll must beEqualTo(Some(i))
      }
      (1 to n).foreach { i =>
        q.poll must beEqualTo(None)
      }
      q.poll must beEqualTo(None)
    }
  }
}
