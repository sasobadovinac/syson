// Generated from DirectEdit.g4 by ANTLR 4.10.1

/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.syson.diagram.general.view.directedit.grammars;

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
}