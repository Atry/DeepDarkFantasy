package com.thoughtworks.DDF.Product

import com.thoughtworks.DDF.Arrow.{ArrowRepr, NextArrow}
import com.thoughtworks.DDF.Combinators.SKIRepr

trait NextProduct[Info[_], Repr[_], Arg] extends
  ProductRepr[Lambda[X => Info[Arg => X]], Lambda[X => Either[Repr[X], Repr[Arg => X]]]] with
  NextArrow[Info, Repr, Arg] {
  implicit def base: ProductRepr[Info, Repr]

  override implicit def productInfo[A, B](implicit ai: Info[Arg => A], bi: Info[Arg => B]) =
    iconv(base.productInfo(convi(ai), convi(bi)))

  override def productZerothInfo[A, B] = x => iconv(base.productZerothInfo(convi(x)))

  override def productFirstInfo[A, B] = x => iconv(base.productFirstInfo(convi(x)))

  override def mkProduct[A, B](implicit ai: Info[Arg => A], bi: Info[Arg => B]) =
    rconv(base.mkProduct[A, B](convi(ai), convi(bi)))

  override def zeroth[A, B](implicit ai: Info[Arg => A], bi: Info[Arg => B]) =
    rconv(base.zeroth(convi(ai), convi(bi)))

  override def first[A, B](implicit ai: Info[Arg => A], bi: Info[Arg => B]) =
    rconv(base.first(convi(ai), convi(bi)))

  override def curry[A, B, C](implicit ai: Info[Arg => A], bi: Info[Arg => B], ci: Info[Arg => C]) =
    rconv(base.curry(convi(ai), convi(bi), convi(ci)))

  override def uncurry[A, B, C](implicit ai: Info[Arg => A], bi: Info[Arg => B], ci: Info[Arg => C]) =
    rconv(base.uncurry(convi(ai), convi(bi), convi(ci)))
}

object NextProduct {
  implicit def apply[Info[_], Repr[_], Arg](implicit
                                            prod: ProductRepr[Info, Repr],
                                            skir: SKIRepr[Info, Repr],
                                            arg: Info[Arg]) =
    new NextProduct[Info, Repr, Arg] {
      override def base: ProductRepr[Info, Repr] = prod

      override implicit def argi: Info[Arg] = arg

      override implicit def ski: SKIRepr[Info, Repr] = skir
  }
}