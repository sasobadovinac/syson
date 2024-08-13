// Generated from DirectEdit.g4 by ANTLR 4.10.1

/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.syson.services.grammars;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DirectEditParser}.
 */
public interface DirectEditListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(DirectEditParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(DirectEditParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#prefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpression(DirectEditParser.PrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#prefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpression(DirectEditParser.PrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#directionPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterDirectionPrefixExpression(DirectEditParser.DirectionPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#directionPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitDirectionPrefixExpression(DirectEditParser.DirectionPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#abstractPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterAbstractPrefixExpression(DirectEditParser.AbstractPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#abstractPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitAbstractPrefixExpression(DirectEditParser.AbstractPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#variationPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterVariationPrefixExpression(DirectEditParser.VariationPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#variationPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitVariationPrefixExpression(DirectEditParser.VariationPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#readonlyPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterReadonlyPrefixExpression(DirectEditParser.ReadonlyPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#readonlyPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitReadonlyPrefixExpression(DirectEditParser.ReadonlyPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#derivedPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterDerivedPrefixExpression(DirectEditParser.DerivedPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#derivedPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitDerivedPrefixExpression(DirectEditParser.DerivedPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#endPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void enterEndPrefixExpression(DirectEditParser.EndPrefixExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#endPrefixExpression}.
	 * @param ctx the parse tree
	 */
	void exitEndPrefixExpression(DirectEditParser.EndPrefixExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#referenceExpression}.
	 * @param ctx the parse tree
	 */
	void enterReferenceExpression(DirectEditParser.ReferenceExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#referenceExpression}.
	 * @param ctx the parse tree
	 */
	void exitReferenceExpression(DirectEditParser.ReferenceExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#multiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicityExpression(DirectEditParser.MultiplicityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#multiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicityExpression(DirectEditParser.MultiplicityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#multiplicityPropExpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicityPropExpression(DirectEditParser.MultiplicityPropExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#multiplicityPropExpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicityPropExpression(DirectEditParser.MultiplicityPropExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#orderedMultiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void enterOrderedMultiplicityExpression(DirectEditParser.OrderedMultiplicityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#orderedMultiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void exitOrderedMultiplicityExpression(DirectEditParser.OrderedMultiplicityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#nonuniqueMultiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void enterNonuniqueMultiplicityExpression(DirectEditParser.NonuniqueMultiplicityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#nonuniqueMultiplicityExpression}.
	 * @param ctx the parse tree
	 */
	void exitNonuniqueMultiplicityExpression(DirectEditParser.NonuniqueMultiplicityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#multiplicityExpressionMember}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicityExpressionMember(DirectEditParser.MultiplicityExpressionMemberContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#multiplicityExpressionMember}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicityExpressionMember(DirectEditParser.MultiplicityExpressionMemberContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#featureExpressions}.
	 * @param ctx the parse tree
	 */
	void enterFeatureExpressions(DirectEditParser.FeatureExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#featureExpressions}.
	 * @param ctx the parse tree
	 */
	void exitFeatureExpressions(DirectEditParser.FeatureExpressionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#subsettingExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubsettingExpression(DirectEditParser.SubsettingExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#subsettingExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubsettingExpression(DirectEditParser.SubsettingExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#redefinitionExpression}.
	 * @param ctx the parse tree
	 */
	void enterRedefinitionExpression(DirectEditParser.RedefinitionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#redefinitionExpression}.
	 * @param ctx the parse tree
	 */
	void exitRedefinitionExpression(DirectEditParser.RedefinitionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#typingExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypingExpression(DirectEditParser.TypingExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#typingExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypingExpression(DirectEditParser.TypingExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void enterValueExpression(DirectEditParser.ValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#valueExpression}.
	 * @param ctx the parse tree
	 */
	void exitValueExpression(DirectEditParser.ValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#measurementExpression}.
	 * @param ctx the parse tree
	 */
	void enterMeasurementExpression(DirectEditParser.MeasurementExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#measurementExpression}.
	 * @param ctx the parse tree
	 */
	void exitMeasurementExpression(DirectEditParser.MeasurementExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#transitionExpression}.
	 * @param ctx the parse tree
	 */
	void enterTransitionExpression(DirectEditParser.TransitionExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#transitionExpression}.
	 * @param ctx the parse tree
	 */
	void exitTransitionExpression(DirectEditParser.TransitionExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#triggerExpression}.
	 * @param ctx the parse tree
	 */
	void enterTriggerExpression(DirectEditParser.TriggerExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#triggerExpression}.
	 * @param ctx the parse tree
	 */
	void exitTriggerExpression(DirectEditParser.TriggerExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#triggerExpressionName}.
	 * @param ctx the parse tree
	 */
	void enterTriggerExpressionName(DirectEditParser.TriggerExpressionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#triggerExpressionName}.
	 * @param ctx the parse tree
	 */
	void exitTriggerExpressionName(DirectEditParser.TriggerExpressionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#guardExpression}.
	 * @param ctx the parse tree
	 */
	void enterGuardExpression(DirectEditParser.GuardExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#guardExpression}.
	 * @param ctx the parse tree
	 */
	void exitGuardExpression(DirectEditParser.GuardExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#effectExpression}.
	 * @param ctx the parse tree
	 */
	void enterEffectExpression(DirectEditParser.EffectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#effectExpression}.
	 * @param ctx the parse tree
	 */
	void exitEffectExpression(DirectEditParser.EffectExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(DirectEditParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(DirectEditParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link DirectEditParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(DirectEditParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link DirectEditParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(DirectEditParser.NameContext ctx);
}